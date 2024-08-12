package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Payment;
import com.fiap.techchallenge.fastfood.core.exceptions.OrderHasPaymentException;

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

}
