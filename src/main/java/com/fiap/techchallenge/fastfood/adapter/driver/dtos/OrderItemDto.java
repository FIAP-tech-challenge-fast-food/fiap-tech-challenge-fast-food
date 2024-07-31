package com.fiap.techchallenge.fastfood.adapter.driver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDto {
    private Long id;
    private ProductDto product;
    private OrderDto order;
    private Double price;
    private Integer quantity;
}
