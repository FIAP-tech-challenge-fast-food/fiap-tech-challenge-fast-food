package com.fiap.techchallenge.fastfood.adapter.driven.infra;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.UserEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.OrderMapper;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.ProductMapper;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.OrderRepository;
import com.fiap.techchallenge.fastfood.core.applications.ports.OrderRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderJpaPort implements OrderRepositoryPort {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void generateOrder(User user, List<OrderItem> orderItems) {
        OrderEntity createdOrder = this.orderRepository.save(OrderMapper.toEntity(new Order(user, orderItems)));

        // TODO: Create a method to save order items with the retrieved order in the createdOrder
    }

    @Override
    public Order findById(Long id) {
        OrderEntity orderEntity = this.orderRepository.getReferenceById(id);

        return OrderMapper.toDomain(orderEntity);
    }

    @Override
    public List<Order> findByStatus(OrderStatus orderStatus) {
        var orderEntities = this.orderRepository.findByStatus(orderStatus);

        return orderEntities.stream().map(OrderMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        var orderEntities = this.orderRepository.findByUser(userEntity);

        return orderEntities.stream().map(OrderMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus newOrderStatus) {
        Optional<OrderEntity> optionalOrder = this.orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            OrderEntity order = optionalOrder.get();
            order.setOrderStatus(newOrderStatus);

            this.orderRepository.save(order);
        } else {
            throw new EntityNotFoundException("Order with id " + orderId + " not found");
        }
    }
}
