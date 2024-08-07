package com.fiap.techchallenge.fastfood.core.exceptions;

import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order not found with id: " + id);
    }

    public OrderNotFoundException(OrderStatus orderStatus) {
        super("No orders found with status: " + orderStatus);
    }
}
