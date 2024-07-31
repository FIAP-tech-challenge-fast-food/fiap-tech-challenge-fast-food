package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.OrderRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.OrderServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.domain.User;

import java.util.List;

public class OrderService implements OrderServicePort {

    private final OrderRepositoryPort orderRepositoryPort;

    public OrderService(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    public Order generateOrder(User user, List<OrderItem> orderItems) {
        return this.orderRepositoryPort.generateOrder(user, orderItems);
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
