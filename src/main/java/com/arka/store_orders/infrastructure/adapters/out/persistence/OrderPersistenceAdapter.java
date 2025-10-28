package com.arka.store_orders.infrastructure.adapters.out.persistence;

import com.arka.store_orders.domain.models.Order;
import com.arka.store_orders.domain.ports.out.persistence.OrderPersistencePort;
import com.arka.store_orders.infrastructure.adapters.out.persistence.repositories.OrderJpaRepository;
import com.arka.store_orders.infrastructure.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistencePort {
    private final OrderMapper mapper;
    private final OrderJpaRepository repository;
    @Override
    public Order save(Order order) {
        return mapper.orderEntityToDomain(repository.save(mapper.orderDomainToEntity(order)));
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return repository.findById(id).map(mapper::orderEntityToDomain);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll().stream().map(mapper::orderEntityToDomain).toList();
    }

    @Override
    public Optional<Order> findByIdWithItems(UUID id) {
        return repository.findByIdWithItems(id).map(mapper::orderEntityToDomain);
    }
}
