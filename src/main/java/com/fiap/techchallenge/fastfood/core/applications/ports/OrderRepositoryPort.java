package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderEntity;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface OrderRepositoryPort {

    Order generateOrder(Order order);

    Order findById(Long id);

    List<Order> findOrdersByQueryParams(Specification<OrderEntity> filters);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);

    void updateOrderReference(Long orderId, String reference);
}
