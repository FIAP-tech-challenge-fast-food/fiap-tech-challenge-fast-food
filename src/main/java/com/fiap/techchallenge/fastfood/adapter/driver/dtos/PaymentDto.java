package com.fiap.techchallenge.fastfood.adapter.driver.dtos;

import java.util.Date;

import com.fiap.techchallenge.fastfood.core.domain.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDto {

    private Long id;

    private String externalReference;

    private Order order;

    private Date createdAt;

}
