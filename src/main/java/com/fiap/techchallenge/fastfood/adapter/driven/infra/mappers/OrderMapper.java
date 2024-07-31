package com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderItemEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.UserEntity;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toDomain(OrderEntity orderEntity, List<OrderItemEntity> orderItemsEntities) {
        if (orderEntity == null) {
            return null;
        }

        User user = null;
        if (orderEntity.getUser() != null) {
            user = new User(
                    orderEntity.getUser().getId(),
                    orderEntity.getUser().getName(),
                    orderEntity.getUser().getEmail(),
                    orderEntity.getUser().getCpf(),
                    orderEntity.getUser().getCreatedAt()
            );
        }

        double totalPrice = orderItemsEntities != null ?
                orderItemsEntities.stream()
                        .mapToDouble(OrderItemEntity::getPrice)
                        .sum() : 0.00;

        List<OrderItem> orderItems = orderItemsEntities != null ?
                orderItemsEntities.stream()
                        .map(OrderItemMapper::toDomain)
                        .collect(Collectors.toList()) : new ArrayList<>();

        return new Order(
                orderEntity.getId(),
                user,
                orderEntity.getOrderStatus(),
                totalPrice,
                orderEntity.getCreatedAt(),
                orderItems
        );
    }

    public static OrderEntity toEntity(Order order) {
        if (order == null) {
            return null;
        }

        UserEntity userEntity = null;
        if (order.getUser() != null) {
            userEntity = new UserEntity(
                    order.getUser().getId(),
                    order.getUser().getName(),
                    order.getUser().getEmail(),
                    order.getUser().getCpf(),
                    order.getUser().getCreatedAt()
            );
        }

        return new OrderEntity(
                order.getId(),
                userEntity,
                order.getOrderStatus(),
                order.getCreatedAt()
        );
    }
}
