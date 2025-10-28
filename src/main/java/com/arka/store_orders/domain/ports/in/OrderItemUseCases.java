package com.arka.store_orders.domain.ports.in;

import com.arka.store_orders.domain.models.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemUseCases {
    OrderItem saveOrderItem(OrderItem item);
    Optional<OrderItem> getById(Long id);
    List<OrderItem> getAll();
    List<OrderItem> getAllByOrderId(UUID id);
    OrderItem updateOrderItem(Long orderItemId,OrderItem item);
    void delete(Long id);
}
