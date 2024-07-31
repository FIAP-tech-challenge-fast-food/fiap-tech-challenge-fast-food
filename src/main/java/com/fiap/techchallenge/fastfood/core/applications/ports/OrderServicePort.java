package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.domain.User;

import java.util.List;

public interface OrderServicePort {

    Order generateOrder(User user, List<OrderItem> orderItems);

    Order findById(Long id);

    List<Order> findByStatus(OrderStatus orderStatus);

    List<Order> findByUserId(Long userId);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
