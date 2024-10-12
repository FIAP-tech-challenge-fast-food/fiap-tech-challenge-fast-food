package com.fiap.techchallenge.fastfood.core.applications.ports;

import java.util.List;

import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

public interface PaymentRepositoryPort {

    Payment registerPayment(Payment payment);

    List<Payment> findAll();

    Payment findByOrderId(Long orderId);

    Payment save(Payment payment);

    Payment findByExternalReference(String externalReference);

}
