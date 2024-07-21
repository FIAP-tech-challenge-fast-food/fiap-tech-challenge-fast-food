package com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.UserEntity;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.User;

public class OrderMapper {

    public static Order toDomain(OrderEntity orderEntity) {
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

        // TODO: Implement method to retrieve the total order value (get the total from the order_items table)
        double totalPrice = 0.00;

        return new Order(
                orderEntity.getId(),
                user,
                orderEntity.getOrderStatus(),
                totalPrice,
                orderEntity.getCreatedAt()
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
