package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.OrderRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.exceptions.InvalidOrderStatusException;
import com.fiap.techchallenge.fastfood.core.exceptions.OrderNotFoundException;

import java.util.List;

public class OrderValidator {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderItemValidator orderItemValidator;
    private final UserValidator userValidator;

    public OrderValidator(OrderRepositoryPort orderRepositoryPort, OrderItemValidator orderItemValidator, UserValidator userValidator) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.orderItemValidator = orderItemValidator;
        this.userValidator = userValidator;
    }

    public void validateOrder(Long userId, List<OrderItem> orderItems) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID cannot be null or less than or equal to zero");
        }

        this.userValidator.validateUserExistsById(userId);

        this.orderItemValidator.validateItems(orderItems);
    }

    public void validateOrderExistsById(Long orderId) {
        if (orderRepositoryPort.findById(orderId) == null) {
            throw new OrderNotFoundException(orderId);
        }
    }

    public void validateOrderStatusExists(String value) {
        if (value == null || value.isEmpty()) {
            throw new InvalidOrderStatusException();
        }

        if (!OrderStatus.isValid(value)) {
            throw new InvalidOrderStatusException(value);
        }
    }
}
