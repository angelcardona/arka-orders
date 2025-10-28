package com.arka.store_orders.application.services;

import com.arka.store_orders.domain.models.OrderItem;
import com.arka.store_orders.domain.ports.in.OrderItemUseCases;
import com.arka.store_orders.domain.ports.out.persistence.OrderItemPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemService implements OrderItemUseCases {

    private final OrderItemPersistencePort persistence;
    @Override
    public OrderItem saveOrderItem(OrderItem item) {
        return persistence.save(item);
    }

    @Override
    public Optional<OrderItem> getById(Long id) {
        return persistence.findById(id);
    }

    @Override
    public List<OrderItem> getAll() {
        return persistence.findAll();
    }

    @Override
    public List<OrderItem> getAllByOrderId(UUID id) {
        return persistence.findAllOrderId(id);
    }

    @Override
    public OrderItem updateOrderItem(Long orderItemId, OrderItem item) {
        OrderItem existingItem=getById(orderItemId)
                .orElseThrow(()->new IllegalArgumentException("OrderId not Found By Id"));
        existingItem.setQuantity(item.getQuantity());
        return saveOrderItem(item);
    }

    @Override
    public void delete(Long id) {
        persistence.delete(id);
    }

}
