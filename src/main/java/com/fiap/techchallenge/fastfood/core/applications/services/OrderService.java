package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.*;
import com.fiap.techchallenge.fastfood.core.domain.*;
import com.fiap.techchallenge.fastfood.core.exceptions.OrderNotFoundException;
import com.fiap.techchallenge.fastfood.core.exceptions.ProductNotFoundException;
import com.fiap.techchallenge.fastfood.core.validators.OrderItemValidator;
import com.fiap.techchallenge.fastfood.core.validators.OrderValidator;
import com.fiap.techchallenge.fastfood.core.validators.UserValidator;

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
        this.orderValidator = new OrderValidator(orderRepositoryPort, new OrderItemValidator(),
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

    @Override
    public List<Order> findAll() {
        return this.orderRepositoryPort.findAll();
    }

    private OrderItem fillOrderItems(OrderItem orderItem, Order order) {
        Product originalProduct = orderItem.getProduct();

        if (originalProduct == null) {
            return null;
        }

        Product product = this.productRepositoryPort.findById(orderItem.getProduct().getId());

        if (product == null) {
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
        totalPrice += orderItems != null ? orderItems.stream()
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

    public List<Order> findByStatus(String orderStatus) {
        this.orderValidator.validateOrderStatusExists(orderStatus);

        OrderStatus status = OrderStatus.valueOf(orderStatus);
        return this.orderRepositoryPort.findByStatus(status);
    }

    public List<Order> findByUserId(Long userId) {
        this.userValidator.validateUserExistsById(userId);
        return this.orderRepositoryPort.findByUserId(userId);
    }

//    public List<Order> findByStatusAndUserId(String orderStatus, Long userId) {
//        this.orderValidator.validateOrderStatusExists(orderStatus);
//        this.userValidator.validateUserExistsById(userId);
//
//        OrderStatus status = OrderStatus.valueOf(orderStatus);
//        return this.orderRepositoryPort.findByStatusAndUserId(status, userId);
//    }

    public void updateOrderStatus(Long orderId, String orderStatus) {
        this.orderValidator.validateOrderExistsById(orderId);
        this.orderValidator.validateOrderStatusExists(orderStatus);

        OrderStatus status = OrderStatus.valueOf(orderStatus);
        this.orderRepositoryPort.updateOrderStatus(orderId, status);
    }
}
