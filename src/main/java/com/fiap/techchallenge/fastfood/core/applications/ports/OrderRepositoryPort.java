package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;

import java.util.List;

public interface OrderRepositoryPort {

    Order generateOrder(Order order);

    List<Order> findAll();

    Order findById(Long id);

    List<Order> findByStatus(OrderStatus orderStatus);

    List<Order> findByUserId(Long userId);

    List<Order> findByStatusAndUserId(OrderStatus orderStatus, Long userId);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
