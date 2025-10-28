package com.arka.store_orders.infrastructure.exceptions;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(String message) {
        super(message);
    }
    public OrderItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
