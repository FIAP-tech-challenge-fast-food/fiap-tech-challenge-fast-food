package com.fiap.techchallenge.fastfood.core.exceptions;

public class OrderAlreadyExistsException extends RuntimeException {
    public OrderAlreadyExistsException(long orderId) {
        super("Order not available: " + orderId);
    }
}

