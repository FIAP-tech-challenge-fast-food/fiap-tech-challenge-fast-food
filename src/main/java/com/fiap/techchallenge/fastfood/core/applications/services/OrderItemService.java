package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.OrderItemRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.OrderItemServicePort;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.validators.OrderItemValidator;

import java.util.List;

public class OrderItemService implements OrderItemServicePort {

    private final OrderItemRepositoryPort orderItemRepositoryPort;
    private final OrderItemValidator orderItemValidator;


    public OrderItemService(OrderItemRepositoryPort orderItemRepositoryPort, ProductRepositoryPort productRepositoryPort,
                            CategoryRepositoryPort categoryRepositoryPort) {
        this.orderItemRepositoryPort = orderItemRepositoryPort;
        this.orderItemValidator = new OrderItemValidator();
    }

    @Override
    public void register(OrderItem orderItem) {
        this.orderItemValidator.validate(orderItem);

        calculatePrice(orderItem);

        this.orderItemRepositoryPort.register(orderItem.getProduct(), orderItem.getOrder(), orderItem.getPrice(), orderItem.getQuantity());
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        //TODO: Colocar o validator order
        return this.orderItemRepositoryPort.findByOrderId(orderId);
    }

    private void calculatePrice(OrderItem orderItem) {
        Double price = orderItem.getProduct().getPrice() * orderItem.getQuantity();

        orderItem.setPrice(price);
    }

}
