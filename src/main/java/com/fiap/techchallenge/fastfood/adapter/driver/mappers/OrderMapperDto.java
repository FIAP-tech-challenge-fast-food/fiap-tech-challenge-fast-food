package com.fiap.techchallenge.fastfood.adapter.driver.mappers;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.OrderDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.OrderItemDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.UserDto;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapperDto {

    public static Order toDomain(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }

        double totalPrice = 0.00;
        totalPrice += orderDto.getOrderItems() != null ?
                orderDto.getOrderItems().stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity())
                        .sum() : 0.00;

        List<OrderItem> orderItems = OrderItemMapperDto.mapToDomain(orderDto.getOrderItems());

        return new Order(
                orderDto.getId(),
                new User(orderDto.getUserId()),
                orderDto.getOrderStatus(),
                orderDto.getReference(),
                totalPrice,
                orderDto.getCreatedAt(),
                orderItems
        );
    }

    public static OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        UserDto userDto = null;
        if (order.getUser() != null) {
            userDto = new UserDto(
                    order.getUser().getId(),
                    order.getUser().getName(),
                    order.getUser().getEmail(),
                    order.getUser().getCpf(),
                    order.getUser().getCreatedAt()
            );
        }

        double totalPrice = 0.00;
        totalPrice += order.getOrderItems() != null ?
                order.getOrderItems().stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity())
                        .sum() : 0.00;

        List<OrderItemDto> orderItemsDto = order.getOrderItems() != null ?
                order.getOrderItems().stream()
                        .map(OrderItemMapperDto::toDto)
                        .collect(Collectors.toList()) : new ArrayList<>();

        return new OrderDto(
                order.getId(),
                userDto.getId(),
                order.getOrderStatus(),
                order.getReference(),
                totalPrice,
                order.getCreatedAt(),
                orderItemsDto
        );
    }
}
