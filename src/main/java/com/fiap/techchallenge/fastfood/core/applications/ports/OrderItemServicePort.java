package com.fiap.techchallenge.fastfood.core.applications.ports;

import java.util.List;

import com.fiap.techchallenge.fastfood.core.domain.OrderItem;

public interface OrderItemServicePort {

    public void register(OrderItem orderItem);

    public List<OrderItem> findByOrderId(Long orderId);

}
