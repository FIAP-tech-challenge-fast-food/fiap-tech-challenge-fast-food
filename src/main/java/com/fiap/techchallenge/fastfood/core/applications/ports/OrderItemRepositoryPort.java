package com.fiap.techchallenge.fastfood.core.applications.ports;

import java.util.List;

import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.Product;

public interface OrderItemRepositoryPort {

    void register(Product product, Order order, Double price, Integer quantity);

    List<OrderItem> findByOrderId(Long orderId);
    
}
