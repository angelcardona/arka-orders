package com.arka.store_orders.infrastructure.exceptions;

public class PaymentFailedException extends RuntimeException {
    public PaymentFailedException(String message) {
        super(message);
    }
    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
