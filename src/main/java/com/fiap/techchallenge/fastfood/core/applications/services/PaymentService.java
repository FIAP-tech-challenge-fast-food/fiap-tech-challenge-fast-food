package com.fiap.techchallenge.fastfood.core.applications.services;

import java.util.List;

import com.fiap.techchallenge.fastfood.core.applications.ports.OrderRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentServicePort;
import com.fiap.techchallenge.fastfood.core.applications.ports.UserRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.domain.Payment;
import com.fiap.techchallenge.fastfood.core.validators.OrderItemValidator;
import com.fiap.techchallenge.fastfood.core.validators.OrderValidator;
import com.fiap.techchallenge.fastfood.core.validators.PaymentValidator;
import com.fiap.techchallenge.fastfood.core.validators.UserValidator;

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
    public Payment registerPayment(Payment payment) {
        PaymentValidator.validate(payment);

        Long orderId = payment.getOrder().getId();
        this.orderValidator.validateOrderExistsById(orderId);

        this.paymentValidator.validateOrderHasPayment(orderId);

        Payment paymentCreated = this.paymentRepositoryPort.registerPayment(payment.getExternalReference(), payment.getOrder());

        updateOrderStatusAccordingToPayment(payment.getExternalReference(), payment.getOrder().getId());

        return paymentCreated;
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
    
    private void updateOrderStatusAccordingToPayment(String externalReference, Long orderId) {
        if (externalReference != null && !externalReference.isEmpty()) {
            this.orderRepositoryPort.updateOrderStatus(orderId, OrderStatus.PAID);
        } else {
            this.orderRepositoryPort.updateOrderStatus(orderId, OrderStatus.CANCELED);
        }
    }

}
