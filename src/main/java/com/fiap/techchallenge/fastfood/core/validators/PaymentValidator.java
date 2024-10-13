package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.domain.Payment;
import com.fiap.techchallenge.fastfood.core.domain.PaymentStatus;

public class PaymentValidator {

    public PaymentValidator() {
    }

    public static void validate(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment item cannot be null");
        }
    }

    public void validatePaymentStatus(String paymentStatus) {
        if (paymentStatus == null) {
            throw new IllegalArgumentException("PaymentStatus cannot be null");
        }

        if (!(PaymentStatus.APPROVED.name().equals(paymentStatus) || PaymentStatus.REFUSED.name().equals(paymentStatus))) {
            throw new IllegalArgumentException("PaymentStatus must be APPROVED or REFUSED");
        }
    }

}
