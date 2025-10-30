package com.arka.store_orders.application.services;

import com.arka.store_orders.application.factory.OrderFactory;
import com.arka.store_orders.domain.models.Order;
import com.arka.store_orders.domain.models.OrderItem;
import com.arka.store_orders.domain.models.OrderStatus;
import com.arka.store_orders.domain.ports.in.OrderUseCases;
import com.arka.store_orders.domain.ports.out.feignclient.PaymentPort;
import com.arka.store_orders.domain.ports.out.feignclient.ProductPort;
import com.arka.store_orders.domain.ports.out.feignclient.ShippingPort;
import com.arka.store_orders.domain.ports.out.persistence.OrderPersistencePort;

import com.arka.store_orders.infrastructure.exceptions.*;
import com.arka.store_orders.infrastructure.resources.Request.ItemQuantityUpdate;
import com.arka.store_orders.infrastructure.resources.Request.OrderRequest;
import com.arka.store_orders.infrastructure.resources.Request.PaymentRequest;
import com.arka.store_orders.infrastructure.resources.Request.ShippingRequest;
import com.arka.store_orders.infrastructure.resources.Response.AvailableStockResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCases {
    private final static Logger log=Logger.getLogger(OrderService.class.getName());


    private final OrderFactory orderFactory;
    private final OrderPersistencePort persistence;
    private final ProductPort productPort;
    private final PaymentPort paymentPort;
    private final ShippingPort shippingPort;

    @Override
    @Transactional
    public Order createOrder(OrderRequest request) {
        Order newOrder=orderFactory.createOrder(request);
        List<OrderItem> items=newOrder.getItems();
        try {
            for (OrderItem item : items) {
                validateAndReserveStock(item);
                item.calculateAmount();
                item.setOrderId(newOrder.getId());
            }
            newOrder.calculateTotal();
            log.info("Order created and stock reserved for orderId: {}"+newOrder.getId());
            return persistence.save(newOrder);
        } catch (Exception e) {
            rollbackStockReservations(items);
            log.warning("Failed to create order due to: {}"+e.getMessage()+ e);
            throw new OrderCreationFailedException("Failed to create order: " + e.getMessage(), e);
        }
    }
    private void validateAndReserveStock(OrderItem item){
        Long id=item.getProductId();
        AvailableStockResponse availableStock=productPort.getAvailableStock(id);
        if (availableStock.availableStock() == null || availableStock.availableStock() < item.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for SKU: " + id + ". Available: " + availableStock);
        }
        productPort.reserveStock(id, item.getQuantity());
    }
    private void rollbackStockReservations(List<OrderItem> items) {
        for (OrderItem item : items) {
            try {
                productPort.recoveryStock(item.getProductId(), item.getQuantity());
            } catch (Exception ignored) {
                log.warning("Failed to rollback stock for SKU: {}");
            }
        }
    }
    @Override
    @Transactional
    public Order processOrder(UUID orderId) {
        Order processingOrder = getOrderById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found by ID: " + orderId));
        processingOrder.calculateTotal();
        PaymentRequest paymentRequest = new PaymentRequest(orderId, processingOrder.getTotal());
        String transactionId;
        try {
            transactionId = paymentPort.processPayment(paymentRequest);
            log.info("Payment processed for orderId: {} with txId: {}"+ orderId+ transactionId);
        } catch (Exception e) {
            log.warning("Payment failed for orderId: {}"+orderId+ e);
            throw new PaymentFailedException("Payment initiation failed for order " + orderId + ". Reason: " + e.getMessage(), e);
        }

        processingOrder.setTransactionId(transactionId);
        processingOrder.switchToWaitingConfirmation();
        return persistence.save(processingOrder);

    }
    @Override
    @Transactional
    public Order updateOrder(UUID id, Long itemId, ItemQuantityUpdate quantityUpdate) {
        Order processingOrder = getOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found by ID: " + id));
        processingOrder.ensureCanModifyItems();
        OrderItem existingItem=getOrderItem(processingOrder,itemId);
        handleSockUpdate(existingItem.getProductId(),existingItem.getQuantity(), quantityUpdate.quantity());
        existingItem.setQuantity(quantityUpdate.quantity());
        existingItem.calculateAmount();
        processingOrder.calculateTotal();
        return persistence.save(processingOrder);



    }
    private void handleSockUpdate(Long productId,Integer oldQuantity,Integer newQuantity){
        int quantityDifference = newQuantity - oldQuantity;

        if (quantityDifference > 0) {
            Integer stockToReserve = quantityDifference;
            AvailableStockResponse stockResponse = productPort.getAvailableStock(productId);
            if (stockResponse.availableStock() < stockToReserve) {
                throw new InsufficientStockException("Insufficient Stock available to update the item. Required: " + stockToReserve);
            }
            productPort.reserveStock(productId, stockToReserve);

        } else if (quantityDifference < 0) {
            Integer stockToRelease = Math.abs(quantityDifference);
            productPort.recoveryStock(productId, stockToRelease);
        }
    }
    private OrderItem getOrderItem(Order order, Long itemId){
        return order.getItems().stream().filter(
                item -> item.getId().equals(itemId)).findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Item not found by ID: " + itemId));

    }

    @Override
    public Order acceptOrder(UUID orderId,String userId) {
        Order acceptedOrder = getOrderById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found by ID: " + orderId));
        acceptedOrder.ensureAcceptOrder();
        acceptedOrder.switchToAccepted();
        acceptedOrder.setUserId(userId);
        for (OrderItem item : acceptedOrder.getItems()) {
            productPort.decrementStock(item.getProductId(), item.getQuantity());
        }

        log.info("Order accepted and stock decremented for orderId: {}"+ orderId);
        return persistence.save(acceptedOrder);
    }

    @Override
    public void cancelOrder(UUID id) {
        Order existingOrder = getOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found by ID: " + id));
        existingOrder.ensureCanRemoveOrder();

        for (OrderItem item : existingOrder.getItems()) {
            productPort.recoveryStock(item.getProductId(), item.getQuantity());
        }
        existingOrder.switchToCanceled();
        persistence.save(existingOrder);
        log.info("Order canceled and stock recovered for orderId: {}"+ id);
    }

    @Override
    public Order deleteItem(UUID orderId, Long itemId) {
        Order existingOrder = getOrderById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found by ID: " + orderId));
        existingOrder.ensureCanModifyItems();
        OrderItem itemToRemove=existingOrder.getItems().stream().filter(
                item -> item.getId().equals(itemId))
                .findFirst().orElseThrow(()->new IllegalArgumentException("Item Not Found"));
        productPort.recoveryStock(itemToRemove.getProductId(),itemToRemove.getQuantity());
        existingOrder.getItems().remove(itemToRemove);
        existingOrder.calculateTotal();
        return  persistence.save(existingOrder);
    }

    @Override
    public Order addItem(UUID orderId, OrderItem item) {
        Order existingOrder = getOrderById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found by ID: " + orderId));

        if (!existingOrder.getStatus().equals(OrderStatus.PENDING)) {
            throw new InvalidOrderStateException("Cannot add items: order is not in PENDING status.");
        }
        validateAndReserveStock(item);
        item.setOrderId(orderId);
        item.calculateAmount();
        existingOrder.addItem(item);
        existingOrder.calculateTotal();
        log.info("Item added to orderId: {}" + orderId);
        return persistence.save(existingOrder);
    }

    @Override
    public void shippingOrder(UUID orderId) {
        Order existingOrder=getOrderById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found by ID: " + orderId));
        existingOrder.ensureShippingOrder();
        ShippingRequest request= new ShippingRequest(existingOrder.getId(), existingOrder.getUserId(),existingOrder.getItems());
        shippingPort.sendOrder(request);

    }

    @Override
    public Optional<Order> getOrderById(UUID id) {
        return persistence.findByIdWithItems(id);
    }

    @Override
    public List<Order> getOrders() {

        return persistence.findAll();
    }
}
