package com.fiap.techchallenge.fastfood.core.exceptions;

public class OrderHasPaymentException extends RuntimeException {
    public OrderHasPaymentException(Long orderId)  {
        super("Order already has a payment for orderId: " + orderId);
    }
}
