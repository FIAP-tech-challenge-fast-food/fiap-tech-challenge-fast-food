package com.fiap.techchallenge.fastfood.core.exceptions;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException(Double price) {
        super("Invalid price for product" + price);
    }
}
