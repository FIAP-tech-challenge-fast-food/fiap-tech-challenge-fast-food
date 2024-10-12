package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.OrderRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentServicePort;
import com.fiap.techchallenge.fastfood.core.applications.ports.UserRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.domain.Payment;
import com.fiap.techchallenge.fastfood.core.exceptions.PaymentRefusedException;
import com.fiap.techchallenge.fastfood.core.utils.UUIDGenerator;
import com.fiap.techchallenge.fastfood.core.domain.PaymentStatus;
import com.fiap.techchallenge.fastfood.core.validators.OrderItemValidator;
import com.fiap.techchallenge.fastfood.core.validators.OrderValidator;
import com.fiap.techchallenge.fastfood.core.validators.PaymentValidator;
import com.fiap.techchallenge.fastfood.core.validators.UserValidator;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentService implements PaymentServicePort {

    private PaymentRepositoryPort paymentRepositoryPort;
    private OrderRepositoryPort orderRepositoryPort;
    private final OrderValidator orderValidator;
    private PaymentValidator paymentValidator;

    public PaymentService(PaymentRepositoryPort paymentRepositoryPort, OrderRepositoryPort orderRepositoryPort, UserRepositoryPort userRepositoryPort) {
        this.paymentRepositoryPort = paymentRepositoryPort;
        this.orderRepositoryPort = orderRepositoryPort;
        this.orderValidator = new OrderValidator(orderRepositoryPort, new OrderItemValidator(), new UserValidator(userRepositoryPort));
        this.paymentValidator = new PaymentValidator(paymentRepositoryPort);
    }

    @Override
    public Payment registerPayment(Order order) {
        this.orderValidator.validateOrderExistsById(order.getId());

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setExternalReference(generatePaymentExternalReference());

        return this.paymentRepositoryPort.registerPayment(payment);
    }

    @Override
    public List<Payment> findAll() {
        return this.paymentRepositoryPort.findAll();
    }

    @Override
    public Payment findByOrderId(Long orderId) {    
        this.orderValidator.validateOrderExistsById(orderId);
        return this.paymentRepositoryPort.findByOrderId(orderId);
    }

    @Override
    public Payment confirmPayment(String externalReference, String paymentStatus) {
        Payment payment = this.paymentRepositoryPort.findByExternalReference(externalReference);
        PaymentValidator.validate(payment);

        this.paymentValidator.validatePaymentStatus(paymentStatus);
        payment.setPaymentStatus(PaymentStatus.valueOf(paymentStatus));

        updateOrderStatusAccordingToPayment(PaymentStatus.valueOf(paymentStatus), externalReference, payment.getOrder().getId());

        return this.paymentRepositoryPort.save(payment);
    }

    private void updateOrderStatusAccordingToPayment(PaymentStatus paymentStatus, String externalReference, Long orderId) {
        if (PaymentStatus.APPROVED.equals(paymentStatus)) {
            this.orderRepositoryPort.updateOrderStatus(orderId, OrderStatus.PAID);
        } else if (PaymentStatus.REFUSED.equals(paymentStatus)) {
            this.orderRepositoryPort.updateOrderStatus(orderId, OrderStatus.WAITING_PAYMENT);
            throw new PaymentRefusedException(orderId, externalReference);
        }
    }

    private String generatePaymentExternalReference() {
        return UUIDGenerator.generateUUID();
    }
}
