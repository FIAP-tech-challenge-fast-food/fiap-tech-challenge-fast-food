package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Payment;
import com.fiap.techchallenge.fastfood.core.domain.PaymentStatus;
import com.fiap.techchallenge.fastfood.core.exceptions.OrderHasPaymentException;

import java.util.EnumSet;
import java.util.Set;

public class PaymentValidator {

    PaymentRepositoryPort paymentRepositoryPort;

    public PaymentValidator(PaymentRepositoryPort paymentRepositoryPort) {
        this.paymentRepositoryPort = paymentRepositoryPort;
    }

    public static void validate(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment item cannot be null");
        }
    }

    public void validateOrderHasPayment(Long orderId) {
        if(this.paymentRepositoryPort.findByOrderId(orderId) != null) {
            throw new OrderHasPaymentException(orderId);
        }
    }

    public void validatePaymentStatus(String paymentStatus) {
        if(paymentStatus == null) {
            throw new IllegalArgumentException("PaymentStatus cannot be null");
        }

        Set<PaymentStatus> approvedOrRefused = EnumSet.of(PaymentStatus.APPROVED, PaymentStatus.REFUSED);
        if (!approvedOrRefused.contains(paymentStatus)) {
            throw new IllegalArgumentException("PaymentStatus must be APPROVED or REFUSED");
        }
    }

}
