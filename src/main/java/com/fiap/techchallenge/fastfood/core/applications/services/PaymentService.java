package com.fiap.techchallenge.fastfood.core.applications.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fiap.techchallenge.fastfood.core.applications.ports.OrderRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentServicePort;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

public class PaymentService implements PaymentServicePort {

    private PaymentRepositoryPort paymentRepositoryPort;
    private final OrderRepositoryPort orderRepositoryPort;

    @Autowired
    public PaymentService(PaymentRepositoryPort paymentRepositoryPort, OrderRepositoryPort orderRepositoryPort) {
        this.paymentRepositoryPort = paymentRepositoryPort;
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public Payment registerPayment(Payment payment) {
        Payment paymentCreated = this.paymentRepositoryPort.registerPayment(payment.getExternalReference(), payment.getOrder(), payment.getCreatedAt());

        updateOrderStatusAccordingToPayment(payment.getExternalReference(), payment.getOrder().getId());

        return paymentCreated;
    }

    @Override
    public List<Payment> findAll() {
        return this.paymentRepositoryPort.findAll();
    }

    @Override
    public Payment findByOrderId(Long orderId) {
        return this.paymentRepositoryPort.findByOrderId(orderId);
    }
    
    private void updateOrderStatusAccordingToPayment(String externalReference, Long orderId) {
        if (externalReference != null && !externalReference.isEmpty()) {
            this.orderRepositoryPort.updateOrderStatus(orderId, OrderStatus.PAID);
        } else {
            this.orderRepositoryPort.updateOrderStatus(orderId, OrderStatus.CANCELED);
        }
    }

}
