package com.arka.store_orders.infrastructure.resources.Request;

import com.arka.store_orders.infrastructure.resources.Response.OrderItemShipping;

import java.util.List;

public record ShippingRequest (
        String orderId,
        String userId,
        List<OrderItemShipping> items
){
}
