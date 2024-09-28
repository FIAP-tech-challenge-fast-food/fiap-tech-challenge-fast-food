package com.fiap.techchallenge.fastfood.adapter.driver.dtos;

import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private OrderStatus orderStatus;
    private PaymentStatus reference;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemDto> orderItems;
}
