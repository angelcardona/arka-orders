package com.arka.store_orders.domain.events;

import com.arka.store_orders.domain.models.OrderItem;

import java.util.List;
import java.util.UUID;

public class OrderCreatedEvent {
    private UUID orderId;
    private List<OrderItem> items;
}
