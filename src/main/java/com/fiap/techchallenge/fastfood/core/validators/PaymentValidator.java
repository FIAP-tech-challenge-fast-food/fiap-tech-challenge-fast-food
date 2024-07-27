package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.domain.Payment;

public class PaymentValidator {

    public static void validate(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment item cannot be null");
        }
    }

}
