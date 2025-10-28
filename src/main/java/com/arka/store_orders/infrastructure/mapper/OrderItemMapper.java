package com.arka.store_orders.infrastructure.mapper;

import com.arka.store_orders.domain.models.OrderItem;
import com.arka.store_orders.infrastructure.adapters.out.persistence.entities.OrderItemEntity;
import com.arka.store_orders.infrastructure.resources.Request.OrderItemRequest;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItemEntity itemToEntity(OrderItem item){
        OrderItemEntity itemEntity=new OrderItemEntity();
        itemEntity.setId(itemEntity.getId());
        itemEntity.setProductId(item.getProductId());
        itemEntity.setQuantity(item.getQuantity());
        itemEntity.setPrice(item.getPrice());
        itemEntity.setAmount(item.getAmount());
        itemEntity.setOrderId(item.getOrderId());
        return itemEntity;
    }
    public OrderItem itemEntityToDomain(OrderItemEntity item){
        return new OrderItem(
                item.getId(),
                item.getProductId(),
                item.getQuantity(),
                item.getPrice(),
                item.getAmount(),
                item.getOrderId()
        );
    }
    public OrderItem requestToDomain(OrderItemRequest request){
        return new OrderItem(
                null,
                request.getProductId(),
                request.getQuantity(),
                request.getPrice(),
                null,
                null
        );
    }

}
