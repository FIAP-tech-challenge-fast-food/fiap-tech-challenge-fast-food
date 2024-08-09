package com.fiap.techchallenge.fastfood.core.exceptions;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException() {
        super("The order status was not provided");
    }

    public InvalidOrderStatusException(String name) {
        super("Order status provided is invalid: " + name);
    }
}
