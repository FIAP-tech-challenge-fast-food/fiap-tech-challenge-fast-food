package com.fiap.techchallenge.fastfood.core.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order not found with id: " + id);
    }

    public OrderNotFoundException() {
        super("No orders found");
    }
}
