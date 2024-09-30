package com.fiap.techchallenge.fastfood.adapter.driver.dtos;

import java.time.LocalDateTime;

import com.fiap.techchallenge.fastfood.core.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDto {

    private Long id;

    private String externalReference;

    private Long orderId;

    private LocalDateTime createdAt;

    private PaymentStatus paymentStatus;

}
