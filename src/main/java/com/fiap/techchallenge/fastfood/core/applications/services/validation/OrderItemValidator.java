package com.fiap.techchallenge.fastfood.core.applications.services.validation;

import com.fiap.techchallenge.fastfood.core.domain.OrderItem;

public class OrderItemValidator {

    public static void validate(OrderItem orderItem) {
        if (orderItem == null) {
            throw new IllegalArgumentException("Order item cannot be null");
        }

        if (orderItem.getProduct() == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (orderItem.getOrder() == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (orderItem.getQuantity() == null || orderItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity cannot be null or less than or equal to zero");
        }
    }

    public static void validateOrderId(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
    }

}
