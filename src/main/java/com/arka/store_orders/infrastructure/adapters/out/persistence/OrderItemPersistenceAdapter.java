package com.arka.store_orders.infrastructure.adapters.out.persistence;

import com.arka.store_orders.domain.models.OrderItem;
import com.arka.store_orders.domain.ports.out.persistence.OrderItemPersistencePort;
import com.arka.store_orders.infrastructure.adapters.out.persistence.repositories.OrderItemJpaRepository;
import com.arka.store_orders.infrastructure.mapper.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderItemPersistenceAdapter implements OrderItemPersistencePort {
    private final OrderItemMapper mapper;
    private final OrderItemJpaRepository repository;

    @Override
    public OrderItem save(OrderItem orderItem) {
        return  mapper.itemEntityToDomain(repository.save(mapper.itemToEntity(orderItem)));
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return repository.findById(id).map(mapper::itemEntityToDomain);
    }

    @Override
    public List<OrderItem> findAll() {
        return repository.findAll().stream().map(mapper::itemEntityToDomain).toList();
    }

    @Override
    public List<OrderItem> findAllOrderId(UUID id) {
        return repository.findAllItemsByOrderId(id).stream().map(mapper::itemEntityToDomain).toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
