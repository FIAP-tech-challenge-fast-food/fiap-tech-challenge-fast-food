package com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.OrderItemDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateOrderRequest {
    private UserDto user;
    private List<OrderItemDto> items;
}
