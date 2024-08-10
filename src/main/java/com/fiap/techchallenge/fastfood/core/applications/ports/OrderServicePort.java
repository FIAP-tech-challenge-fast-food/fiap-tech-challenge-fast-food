package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;

import java.util.List;

public interface OrderServicePort {

    Order generateOrder(Long userId, List<OrderItem> orderItems);

    List<Order> findAll();

    Order findById(Long id);

    List<Order> findByStatus(String orderStatus);

    List<Order> findByUserId(Long userId);

    List<Order> findOrdersByQueryParams(String orderStatus, Long userId);

    void updateOrderStatus(Long orderId, String orderStatus);
}
