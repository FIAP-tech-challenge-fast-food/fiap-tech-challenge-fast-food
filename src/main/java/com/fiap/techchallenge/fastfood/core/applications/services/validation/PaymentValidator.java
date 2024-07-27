package com.fiap.techchallenge.fastfood.core.applications.services.validation;

import com.fiap.techchallenge.fastfood.core.domain.Payment;

public class PaymentValidator {

    public static void validate(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Order item cannot be null");
        }

        if (payment.getOrder() == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
    }

}
