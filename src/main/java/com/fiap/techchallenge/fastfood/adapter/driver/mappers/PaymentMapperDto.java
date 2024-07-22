package com.fiap.techchallenge.fastfood.adapter.driver.mappers;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.PaymentDto;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

public class PaymentMapperDto {

    public static Payment toDomain(PaymentDto paymentDto) {
        if (paymentDto == null) {
            return null;
        }

        Order order = null;
        
        if (paymentDto.getOrder() != null) {

        }

        return new Payment(
                paymentDto.getId(),
                paymentDto.getExternalReference(),
                order,
                paymentDto.getCreatedAt());
    }

    public static PaymentDto toDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        // OrderDto orderDto = null;
        
        if (payment.getOrder() != null) {

        }

        return new PaymentDto(
                payment.getId(), 
                payment.getExternalReference(), 
                null, 
                payment.getCreatedAt());
    }

}
