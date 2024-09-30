package com.fiap.techchallenge.fastfood.adapter.driver.mappers;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.PaymentDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.CreatePaymentRequest;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

public class PaymentMapperDto {

    public static Payment toDomain(PaymentDto paymentDto) {
        if (paymentDto == null) {
            return null;
        }

        Order order = null;
        
        if (paymentDto.getOrderId() != null) {
            order = new Order(paymentDto.getOrderId());
        }

        return new Payment(
                paymentDto.getId(),
                paymentDto.getExternalReference(),
                order,
                paymentDto.getCreatedAt(),
                paymentDto.getPaymentStatus());
    }

    public static Payment toDomain(CreatePaymentRequest createPaymentRequest) {
        if (createPaymentRequest == null) {
            return null;
        }

        Order order = null;
        
        if (createPaymentRequest.getOrderId() != null) {
            order = new Order(createPaymentRequest.getOrderId());
        }

        return new Payment(
                createPaymentRequest.getExternalReference(),
                order);
    }

    public static PaymentDto toDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        Long orderId = null;
        
        if (payment.getOrder() != null) {
            orderId = payment.getOrder().getId();
        }

        return new PaymentDto(
                payment.getId(), 
                payment.getExternalReference(), 
                orderId, 
                payment.getCreatedAt(),
                payment.getPaymentStatus());
    }

}
