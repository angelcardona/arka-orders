package com.arka.store_orders.domain.ports.out.persistence;

import com.arka.store_orders.domain.models.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemPersistencePort {
    OrderItem save(OrderItem orderItem);
    Optional<OrderItem> findById(Long id);
    List<OrderItem> findAll();
    List<OrderItem>findAllOrderId(UUID id);
    void delete(Long id);
}
