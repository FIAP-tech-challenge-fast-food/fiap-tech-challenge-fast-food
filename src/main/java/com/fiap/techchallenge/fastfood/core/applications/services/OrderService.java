package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderEntity;
import com.fiap.techchallenge.fastfood.core.applications.ports.*;
import com.fiap.techchallenge.fastfood.core.domain.*;
import com.fiap.techchallenge.fastfood.core.exceptions.OrderNotFoundException;
import com.fiap.techchallenge.fastfood.core.exceptions.ProductNotFoundException;
import com.fiap.techchallenge.fastfood.core.utils.UUIDGenerator;
import com.fiap.techchallenge.fastfood.core.validators.OrderItemValidator;
import com.fiap.techchallenge.fastfood.core.validators.OrderValidator;
import com.fiap.techchallenge.fastfood.core.validators.UserValidator;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class OrderService implements OrderServicePort {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderValidator orderValidator;
    private final UserValidator userValidator;
    private final ProductRepositoryPort productRepositoryPort;
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final PaymentService paymentService;

    private static final int DEFAULT_ORDER_REFERENCE_LENGTH = 6;

    public OrderService(OrderRepositoryPort orderRepositoryPort, OrderItemRepositoryPort orderItemRepositoryPort,
            ProductRepositoryPort productRepositoryPort, CategoryRepositoryPort categoryRepositoryPort,
            UserRepositoryPort userRepositoryPort, PaymentRepositoryPort paymentRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.orderValidator = new OrderValidator(orderRepositoryPort, new OrderItemValidator(),
                new UserValidator(userRepositoryPort));
        this.userValidator = new UserValidator(userRepositoryPort);
        this.productRepositoryPort = productRepositoryPort;
        this.paymentRepositoryPort = paymentRepositoryPort;
        this.paymentService = new PaymentService(paymentRepositoryPort, orderRepositoryPort, userRepositoryPort);
    }

    public Order generateOrder(Long userId, List<OrderItem> orderItems) {
        this.orderValidator.validateOrder(userId, orderItems);

        Order order = new Order(new User(userId), orderItems);
        order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

        List<OrderItem> filledOrderItems = orderItems.stream().map(x -> fillOrderItems(x, order)).toList();
        order.setOrderItems(filledOrderItems);

        double totalPrice = calculateTotalPrice(filledOrderItems);
        order.setTotalPrice(totalPrice);

        String reference = generateOrderReference(order.getId());
        order.setReference(reference);

        Order savedOrder = this.orderRepositoryPort.generateOrder(order);

        this.paymentService.registerPayment(savedOrder);

        return savedOrder;
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
        orderItem.setPrice(product.getPrice());
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

    public List<Order> findOrdersByQueryParams(String orderStatus, Long userId) {
        Specification<OrderEntity> filters = Specification.where(null);

        if (orderStatus != null) {
            this.orderValidator.validateOrderStatusExists(orderStatus);
            filters = filters.and((root, query, cb) -> cb.equal(root.get("orderStatus"), OrderStatus.valueOf(orderStatus)));
        }

        if (userId != null) {
            this.userValidator.validateUserExistsById(userId);
            filters = filters.and((root, query, cb) -> cb.equal(root.get("user").get("id"), userId));
        }

        List<Order> orders = this.orderRepositoryPort.findOrdersByQueryParams(filters);

        if (orders.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return orders;
    }

    public void updateOrderStatus(Long orderId, String orderStatus) {
        this.orderValidator.validateOrderStatusExists(orderStatus);
        this.orderValidator.validateOrderExistsById(orderId);
        this.orderValidator.validateOrderCanChangeStatusTo(orderId, OrderStatus.valueOf(orderStatus));

        this.orderRepositoryPort.updateOrderStatus(orderId, OrderStatus.valueOf(orderStatus));
    }

    public void updateOrderReference(Long orderId, String reference) {
        this.orderValidator.validateOrderExistsById(orderId);

        this.orderRepositoryPort.updateOrderReference(orderId, reference);
    }

    public Payment findPaymentByOrderId(Long orderId) {
        this.orderValidator.validateOrderExistsById(orderId);

        return this.paymentRepositoryPort.findByOrderId(orderId);
    }

    private String generateOrderReference(Long orderId) {
        return UUIDGenerator.generateUUID().substring(0, DEFAULT_ORDER_REFERENCE_LENGTH).toUpperCase();
    }
}
