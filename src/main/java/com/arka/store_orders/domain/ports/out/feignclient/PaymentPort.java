package com.arka.store_orders.domain.ports.out.feignclient;

import com.arka.store_orders.infrastructure.resources.Request.PaymentRequest;

import java.util.UUID;

public interface PaymentPort {
    String processPayment(PaymentRequest paymentRequest);
    boolean validPayment(String transactionId);
}
