package com.arka.store_orders.application.factory;

import com.arka.store_orders.domain.models.Order;
import com.arka.store_orders.domain.models.OrderItem;
import com.arka.store_orders.infrastructure.mapper.OrderItemMapper;
import com.arka.store_orders.infrastructure.mapper.OrderMapper;
import com.arka.store_orders.infrastructure.resources.Request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderFactory {
    private final OrderItemMapper itemMapper;
    public Order createOrder(OrderRequest request){
        List<OrderItem> items = request.items().stream().map(itemMapper::requestToDomain).toList();
        return new Order(items);

    }
}
