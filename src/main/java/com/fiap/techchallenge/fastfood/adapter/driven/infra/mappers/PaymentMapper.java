package com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.PaymentEntity;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

public class PaymentMapper {

    public static Payment toDomain(PaymentEntity paymentEntity) {
        if (paymentEntity == null) {
            return null;
        }

        Order order = null;

        if (paymentEntity.getOrder() != null) {
            order = OrderMapper.toDomain(paymentEntity.getOrder(), null);
        }

        return new Payment(
                paymentEntity.getId(),
                paymentEntity.getExternalReference(),
                order,
                paymentEntity.getCreatedAt(),
                paymentEntity.getPaymentStatus());
    }

    public static PaymentEntity toEntity(Payment payment) {
        if (payment == null) {
            return null;
        }
        
        OrderEntity order = null;

        if (payment.getOrder() != null) {
            order = OrderMapper.toEntity(payment.getOrder());
        }

        return new PaymentEntity(
                payment.getId(),
                payment.getExternalReference(),
                order,
                payment.getCreatedAt(),
                payment.getPaymentStatus());
    }

}
