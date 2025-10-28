package com.arka.store_orders.infrastructure.resources.Request;

import java.util.UUID;

public record PaymentRequest(UUID orderId,Double amount) {
}
