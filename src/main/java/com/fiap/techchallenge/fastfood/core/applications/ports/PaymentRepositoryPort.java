package com.fiap.techchallenge.fastfood.core.applications.ports;

import java.util.Date;
import java.util.List;

import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

public interface PaymentRepositoryPort {

    Payment registerPayment(String externalReference, Order order, Date createdAt);

    List<Payment> findAll();

    Payment findByOrderId(Long orderId);

}
