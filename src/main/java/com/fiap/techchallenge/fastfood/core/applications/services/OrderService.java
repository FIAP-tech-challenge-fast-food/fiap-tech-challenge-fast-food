package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.*;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.validators.*;

import java.util.List;

public class OrderService implements OrderServicePort {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderValidator orderValidator;

    public OrderService(OrderRepositoryPort orderRepositoryPort, OrderItemRepositoryPort orderItemRepositoryPort,
                        ProductRepositoryPort productRepositoryPort, CategoryRepositoryPort categoryRepositoryPort,
                        UserRepositoryPort userRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.orderValidator = new OrderValidator(orderRepositoryPort, new OrderItemValidator(orderItemRepositoryPort,
                new ProductValidator(productRepositoryPort, new CategoryValidator(categoryRepositoryPort))),
                new UserValidator(userRepositoryPort));
    }

    public Order generateOrder(Long userId, List<OrderItem> orderItems) {
        this.orderValidator.validateOrder(userId, orderItems);
        return this.orderRepositoryPort.generateOrder(userId, orderItems);
    }

    public Order findById(Long id) {
        return this.orderRepositoryPort.findById(id);
    }

    public List<Order> findByStatus(OrderStatus orderStatus) {
        return this.orderRepositoryPort.findByStatus(orderStatus);
    }

    public List<Order> findByUserId(Long userId) {
        return this.orderRepositoryPort.findByUserId(userId);
    }

    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        this.orderRepositoryPort.updateOrderStatus(orderId, orderStatus);
    }
}
