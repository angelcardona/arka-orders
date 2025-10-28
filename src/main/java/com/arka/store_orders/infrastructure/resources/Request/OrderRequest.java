package com.arka.store_orders.infrastructure.resources.Request;

import com.arka.store_orders.domain.models.OrderItem;

import java.util.List;

public record OrderRequest(
        List<OrderItemRequest> items
) {
}
