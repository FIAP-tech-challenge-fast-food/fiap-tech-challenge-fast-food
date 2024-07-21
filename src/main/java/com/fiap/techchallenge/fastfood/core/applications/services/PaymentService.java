package com.fiap.techchallenge.fastfood.core.applications.services;

import java.util.List;

import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

public class PaymentService implements PaymentServicePort {

    private PaymentRepositoryPort paymentRepositoryPort;

    public PaymentService(PaymentRepositoryPort paymentRepositoryPort) {
        this.paymentRepositoryPort = paymentRepositoryPort;
    }

    @Override
    public Payment registerPayment(Payment payment) {
        return this.paymentRepositoryPort.registerPayment(payment.getExternalReference(), payment.getOrder(), payment.getCreatedAt());
    }

    @Override
    public List<Payment> findAll() {
        return this.paymentRepositoryPort.findAll();
    }

    @Override
    public Payment findByOrderId(Long orderId) {
        return this.paymentRepositoryPort.findByOrderId(orderId);
    }

}
