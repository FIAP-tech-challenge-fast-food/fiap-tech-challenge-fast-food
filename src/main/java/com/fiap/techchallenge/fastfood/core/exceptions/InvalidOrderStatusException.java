package com.fiap.techchallenge.fastfood.core.exceptions;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException() {
        super("Order status provided is invalid");
    }
}
