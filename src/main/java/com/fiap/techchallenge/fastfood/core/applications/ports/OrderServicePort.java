package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.domain.User;

import java.util.List;

public interface OrderServicePort {

    public Order generateOrder(User user, List<OrderItem> orderItems);

    public Order findById(Long id);

    public List<Order> findByStatus(OrderStatus orderStatus);

    public List<Order> findByUserId(Long userId);

    public void updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
