package com.arka.store_orders.infrastructure.mapper;

import com.arka.store_orders.domain.models.Order;
import com.arka.store_orders.domain.models.OrderItem;
import com.arka.store_orders.infrastructure.adapters.out.persistence.entities.OrderEntity;
import com.arka.store_orders.infrastructure.adapters.out.persistence.entities.OrderItemEntity;
import com.arka.store_orders.infrastructure.resources.Request.OrderRequest;
import com.arka.store_orders.infrastructure.resources.Response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper itemMapper;

    public Order orderEntityToDomain(OrderEntity order){
        List<OrderItem> items=order.getItems().stream()
                .map(itemMapper::itemEntityToDomain).collect(Collectors.toList());;
        Order mapperOrder=new Order();
        mapperOrder.setId(order.getId());
        mapperOrder.setCreateAt(order.getCreateAt());
        mapperOrder.setItems(items);
        mapperOrder.setStatus(order.getStatus());
        mapperOrder.setTotal(order.getTotal());
        mapperOrder.setUpdateAt(order.getUpdateAt());
        return mapperOrder;
    }
    public OrderEntity orderDomainToEntity(Order order){
        List<OrderItemEntity> items=order.getItems().stream()
                .map(itemMapper::itemToEntity).collect(Collectors.toList());;
        return new OrderEntity(
        order.getId(),
        order.getCreateAt(),
        items,
        order.getStatus(),
        order.getTotal(),
        order.getUpdateAt()
        );
    }
    public OrderResponse domainToResponse(Order order){
        return new OrderResponse(
                order.getId(),
                order.getCreateAt(),
                order.getItems(),
                order.getStatus(),
                order.getTotal()
        );
    }

}
