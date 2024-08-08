package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.*;
import com.fiap.techchallenge.fastfood.core.domain.*;
import com.fiap.techchallenge.fastfood.core.exceptions.OrderNotFoundException;
import com.fiap.techchallenge.fastfood.core.exceptions.ProductNotFoundException;
import com.fiap.techchallenge.fastfood.core.validators.*;

import java.util.List;

public class OrderService implements OrderServicePort {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderValidator orderValidator;
    private final UserValidator userValidator;
    private final ProductRepositoryPort productRepositoryPort;

    public OrderService(OrderRepositoryPort orderRepositoryPort, OrderItemRepositoryPort orderItemRepositoryPort,
                        ProductRepositoryPort productRepositoryPort, CategoryRepositoryPort categoryRepositoryPort,
                        UserRepositoryPort userRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.orderValidator = new OrderValidator(orderRepositoryPort, new OrderItemValidator(orderItemRepositoryPort,
                new ProductValidator(productRepositoryPort, new CategoryValidator(categoryRepositoryPort))),
                new UserValidator(userRepositoryPort));
        this.userValidator = new UserValidator(userRepositoryPort);
        this.productRepositoryPort = productRepositoryPort;
    }

    public Order generateOrder(Long userId, List<OrderItem> orderItems) {
        this.orderValidator.validateOrder(userId, orderItems);

        Order order = new Order(new User(userId), orderItems);
        order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

        List<OrderItem> filledOrderItems = orderItems.stream().map(x -> fillOrderItems(x, order)).toList();
        order.setOrderItems(filledOrderItems);

        double totalPrice = calculateTotalPrice(filledOrderItems);
        order.setTotalPrice(totalPrice);

        return this.orderRepositoryPort.generateOrder(order);
    }

    private OrderItem fillOrderItems(OrderItem orderItem, Order order) {
        Product originalProduct = orderItem.getProduct();

        if(originalProduct == null) {
            return null;
        }

        Product product = this.productRepositoryPort.findById(orderItem.getProduct().getId());

        if(product == null) {
            throw new ProductNotFoundException(orderItem.getProduct().getId());
        }

        orderItem.setProduct(product);
        orderItem.setPrice(getOrderItemPrice(product.getPrice(), orderItem.getQuantity()));
        orderItem.setOrder(order);

        return orderItem;
    }

    private static double getOrderItemPrice(Double productPrice, int orderItemQuantity) {
        return productPrice * orderItemQuantity;
    }

    private static double calculateTotalPrice(List<OrderItem> orderItems) {
        double totalPrice = 0.00;
        totalPrice += orderItems != null ?
                orderItems.stream()
                        .mapToDouble(item -> getOrderItemPrice(item.getPrice(), item.getQuantity()))
                        .sum() : 0.00;
        return totalPrice;
    }

    public Order findById(Long id) {
        Order order = this.orderRepositoryPort.findById(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }
        return order;
    }

    public List<Order> findByStatus(OrderStatus orderStatus) {
        this.orderValidator.validateOrderStatusExists(orderStatus.getValue());
        return this.orderRepositoryPort.findByStatus(orderStatus);
    }

    public List<Order> findByUserId(Long userId) {
        this.userValidator.validateUserExistsById(userId);
        return this.orderRepositoryPort.findByUserId(userId);
    }

    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        this.orderValidator.validateOrderExistsById(orderId);
        this.orderValidator.validateOrderStatusExists(orderStatus.getValue());
        this.orderRepositoryPort.updateOrderStatus(orderId, orderStatus);
    }
}
