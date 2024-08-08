package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.OrderItemRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;

import java.util.List;

public class OrderItemValidator {

    private final OrderItemRepositoryPort orderItemRepositoryPort;
    private final ProductValidator productValidator;

    public OrderItemValidator(OrderItemRepositoryPort orderItemRepositoryPort, ProductValidator productValidator) {
        this.orderItemRepositoryPort = orderItemRepositoryPort;
        this.productValidator = productValidator;
    }

    public void validate(OrderItem orderItem) {
        if (orderItem == null) {
            throw new IllegalArgumentException("Order item cannot be null");
        }

        if (orderItem.getProduct() == null || orderItem.getProduct().getId() == null) {
            throw new IllegalArgumentException("Product in order item cannot be null");
        }

        if (orderItem.getProduct().getId() <= 0) {
            throw new IllegalArgumentException("Product ID in order item cannot be less than or equal to zero");
        }

        this.productValidator.validateProductExistsById(orderItem.getProduct().getId());

        if (orderItem.getQuantity() == null || orderItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Product quantity in order item cannot be null or less than or equal to zero");
        }

        if (orderItem.getPrice() == null || orderItem.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price in order item cannot be null or less than or equal to zero");
        }
    }

    public void validateItems(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be null or empty");
        }

        for (OrderItem item : orderItems) {
            validate(item);
        }
    }
}
