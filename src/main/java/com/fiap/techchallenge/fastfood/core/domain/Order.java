package com.fiap.techchallenge.fastfood.core.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private Long id;
    private User user;
    private OrderStatus orderStatus;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItem> orderItems;

    public Order(Long id, User user, OrderStatus orderStatus, Double totalPrice, LocalDateTime createdAt, List<OrderItem> orderItems) {
        this.id = id;
        this.user = user;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.orderItems = orderItems;
    }

    public Order(User user, List<OrderItem> orderItems) {
        this.user = user;
        this.orderItems = orderItems;
    }

    public Order(Long id) {
        this.id = id;
    }

    public Order(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
