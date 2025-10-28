package com.arka.store_orders.domain.ports.out.persistence;

import com.arka.store_orders.domain.models.Order;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderPersistencePort {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    List<Order> findAll();
    Optional<Order> findByIdWithItems(UUID id);

}
