package com.fiap.techchallenge.fastfood.core.exceptions;

public class InvalidOrderQuantityException extends RuntimeException {
    public InvalidOrderQuantityException() {
        super("Quantity cannot be null or less than or equal to zero");
    }
}
