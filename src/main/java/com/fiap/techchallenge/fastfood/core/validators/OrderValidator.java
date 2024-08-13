package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.OrderRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.exceptions.InvalidOrderStatusException;
import com.fiap.techchallenge.fastfood.core.exceptions.OrderNotFoundException;
import com.fiap.techchallenge.fastfood.core.exceptions.UpdateOrderStatusException;

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

    public void validateOrderCanChangeStatusTo(Long orderId, OrderStatus newStatus) {
        Order order = orderRepositoryPort.findById(orderId);

        if (order.getOrderStatus() == OrderStatus.CANCELED) {
            throw new UpdateOrderStatusException("The order cannot be modified because it is already canceled");
        }

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new UpdateOrderStatusException("The order cannot be modified because it is already delivered");
        }

        if (order.getOrderStatus() == OrderStatus.WAITING_PAYMENT &&
                (newStatus != OrderStatus.PAID && newStatus != OrderStatus.CANCELED)) {
            throw new UpdateOrderStatusException("The order is awaiting payment");
        }

        if (order.getOrderStatus() == OrderStatus.PAID && newStatus != OrderStatus.IN_PREPARATION) {
            throw new UpdateOrderStatusException("The order is paid and awaiting preparation");
        }

        if (order.getOrderStatus() == OrderStatus.IN_PREPARATION && newStatus != OrderStatus.READY) {
            throw new UpdateOrderStatusException("The order is in preparation waiting to be finalized");
        }

        if (order.getOrderStatus() == OrderStatus.READY && newStatus != OrderStatus.DELIVERED) {
            throw new UpdateOrderStatusException("The order is ready and waiting to be delivered");
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
