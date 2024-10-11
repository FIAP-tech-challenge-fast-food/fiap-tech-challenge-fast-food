package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

import java.util.List;

public interface OrderServicePort {

    Order generateOrder(Long userId, List<OrderItem> orderItems);

    Order findById(Long id);

    List<Order> findOrdersByQueryParams(String orderStatus, Long userId);

    void updateOrderStatus(Long orderId, String orderStatus);

    Payment findPaymentByOrderId(Long orderId);
}
