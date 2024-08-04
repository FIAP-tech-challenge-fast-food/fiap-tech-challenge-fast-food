package com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests;

import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateOrderStatusRequest {
    private OrderStatus newStatus;
}
