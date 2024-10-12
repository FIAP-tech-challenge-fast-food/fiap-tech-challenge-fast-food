package com.fiap.techchallenge.fastfood.core.applications.ports;

import java.util.List;

import com.fiap.techchallenge.fastfood.core.domain.Payment;

public interface PaymentServicePort {

    public Payment registerPayment(Payment payment);

    public List<Payment> findAll();

    public Payment findByOrderId(Long orderId);

    public Payment confirmPayment(String externalReference, String paymentStatus);

}
