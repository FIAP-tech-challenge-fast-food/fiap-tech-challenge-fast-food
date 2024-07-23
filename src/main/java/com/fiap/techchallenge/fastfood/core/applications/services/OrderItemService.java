package com.fiap.techchallenge.fastfood.core.applications.services;

import java.util.List;

import com.fiap.techchallenge.fastfood.core.applications.ports.OrderItemRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.OrderItemServicePort;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;

public class OrderItemService implements OrderItemServicePort {

    private OrderItemRepositoryPort orderItemRepositoryPort;

    public OrderItemService(OrderItemRepositoryPort orderItemRepositoryPort) {
        this.orderItemRepositoryPort = orderItemRepositoryPort;
    }

    @Override
    public void register(OrderItem orderItem) {
        calculatePrice(orderItem);

        this.orderItemRepositoryPort.register(orderItem.getProduct(), orderItem.getOrder(), orderItem.getPrice(), orderItem.getQuantity());
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return this.orderItemRepositoryPort.findByOrderId(orderId);
    }

    private void calculatePrice(OrderItem orderItem) {
        Double price = orderItem.getProduct().getPrice() * orderItem.getQuantity();

        orderItem.setPrice(price);
    }

}
