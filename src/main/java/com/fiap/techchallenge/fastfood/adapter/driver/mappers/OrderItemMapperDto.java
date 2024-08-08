package com.fiap.techchallenge.fastfood.adapter.driver.mappers;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.OrderItemDto;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderItemMapperDto {

    public static OrderItem toDomain(OrderItemDto orderItemDto) {
        if (orderItemDto == null) {
            return null;
        }

        return new OrderItem(orderItemDto.getId(),
                new Product(orderItemDto.getProduct()),
                new Order(orderItemDto.getOrder()),
                orderItemDto.getPrice(),
                orderItemDto.getQuantity());
    }

    public static OrderItemDto toDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        return new OrderItemDto(orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getOrder().getId(),
                orderItem.getPrice(),
                orderItem.getQuantity());
    }

    public static List<OrderItem> mapToDomain(List<OrderItemDto> items) {
        return items != null ?
                items.stream().map(OrderItemMapperDto::toDomain).collect(Collectors.toList()) : new ArrayList<>();
    }
}
