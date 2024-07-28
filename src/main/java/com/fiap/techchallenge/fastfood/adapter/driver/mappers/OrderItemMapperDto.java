package com.fiap.techchallenge.fastfood.adapter.driver.mappers;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.OrderDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.OrderItemDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.ProductDto;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.Product;

public class OrderItemMapperDto {

    public static OrderItem toDomain(OrderItemDto orderItemDto) {
        if (orderItemDto == null) {
            return null;
        }

        Product product = null;
        Order order = null;

        if (orderItemDto.getProduct() != null) {
            product = ProductMapperDto.toDomain(orderItemDto.getProduct());
        }

        if (orderItemDto.getOrder() != null) {
            order = OrderMapperDto.toDomain(orderItemDto.getOrder());
        }

        return new OrderItem(orderItemDto.getId(),
                product,
                order,
                orderItemDto.getPrice(),
                orderItemDto.getQuantity());
    }

    public static OrderItemDto toDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        ProductDto product = null;
        OrderDto order = null;

        if (orderItem.getProduct() != null) {
            product = ProductMapperDto.toDto(orderItem.getProduct());
        }

        if (orderItem.getOrder() != null) {
            order = OrderMapperDto.toDto(orderItem.getOrder());
        }

        return new OrderItemDto(orderItem.getId(),
                product,
                order,
                orderItem.getPrice(),
                orderItem.getQuantity());
    }
}
