package com.arka.store_orders.infrastructure.exceptions;

public class OrderPaymentCommunicationException extends RuntimeException {
    public OrderPaymentCommunicationException(String message) {
        super(message);
    }
}
