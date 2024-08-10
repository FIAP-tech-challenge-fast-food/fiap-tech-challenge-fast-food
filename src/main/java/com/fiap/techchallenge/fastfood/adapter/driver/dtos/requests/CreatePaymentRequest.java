package com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePaymentRequest {

    private String externalReference;

    private Long orderId;

    private LocalDateTime createdAt;

}
