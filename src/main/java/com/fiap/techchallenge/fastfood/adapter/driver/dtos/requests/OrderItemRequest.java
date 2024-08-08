package com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemRequest {
    private Long productId;
    private int quantity;
}
