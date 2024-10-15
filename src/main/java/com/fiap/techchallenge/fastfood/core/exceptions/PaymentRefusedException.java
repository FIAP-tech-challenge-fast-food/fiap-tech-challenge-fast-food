package com.fiap.techchallenge.fastfood.core.exceptions;

public class PaymentRefusedException extends RuntimeException {
    public PaymentRefusedException(Long orderId, String externalReference)  {
        super("Payment was refused for orderId: " + orderId + " and external reference: " + externalReference + " - please try again.");
    }
}
