package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.exceptions.InvalidOrderQuantityException;

public class OrderItemValidator {

    public static void validate(OrderItem orderItem) {
        if (orderItem == null) {
            throw new IllegalArgumentException("Order item cannot be null");
        }

        if (orderItem.getQuantity() == null || orderItem.getQuantity() <= 0) {
            throw new InvalidOrderQuantityException();
        }
    }

}
