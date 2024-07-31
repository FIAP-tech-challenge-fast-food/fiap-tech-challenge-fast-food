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

        User user = null;
        if (orderDto.getUser() != null) {
            user = new User(
                    orderDto.getUser().getId(),
                    orderDto.getUser().getName(),
                    orderDto.getUser().getEmail(),
                    orderDto.getUser().getCpf(),
                    orderDto.getUser().getCreatedAt()
            );
        }

        double totalPrice = orderDto.getOrderItems() != null ?
                orderDto.getOrderItems().stream()
                        .mapToDouble(OrderItemDto::getPrice)
                        .sum() : 0.00;

        List<OrderItem> orderItems = OrderItemMapperDto.mapToDomain(orderDto.getOrderItems());

        return new Order(
                orderDto.getId(),
                user,
                orderDto.getOrderStatus(),
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

        double totalPrice = order.getOrderItems() != null ?
                order.getOrderItems().stream()
                        .mapToDouble(OrderItem::getPrice)
                        .sum() : 0.00;

        List<OrderItemDto> orderItemsDto = order.getOrderItems() != null ?
                order.getOrderItems().stream()
                        .map(OrderItemMapperDto::toDto)
                        .collect(Collectors.toList()) : new ArrayList<>();

        return new OrderDto(
                order.getId(),
                userDto,
                order.getOrderStatus(),
                totalPrice,
                order.getCreatedAt(),
                orderItemsDto
        );
    }
}
