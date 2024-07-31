package com.fiap.techchallenge.fastfood.adapter.driven.infra;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderItemEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.UserEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.OrderItemMapper;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.OrderMapper;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.OrderItemRepository;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.OrderRepository;
import com.fiap.techchallenge.fastfood.core.applications.ports.OrderRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderJpaPort implements OrderRepositoryPort {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Order generateOrder(User user, List<OrderItem> orderItems) {
        OrderEntity createdOrder = this.orderRepository.save(OrderMapper.toEntity(new Order(user)));

        List<OrderItemEntity> orderItemsEntities = orderItems.stream()
                .map(orderItem -> {
                    OrderItemEntity orderItemEntity = OrderItemMapper.toEntity(orderItem);
                    orderItemEntity.setOrder(createdOrder);
                    return orderItemEntity;
                })
                .collect(Collectors.toList());

        List<OrderItemEntity> orderItemEntities = this.orderItemRepository.saveAll(orderItemsEntities);

        return OrderMapper.toDomain(createdOrder, orderItemEntities);
    }

    @Override
    public Order findById(Long id) {
        OrderEntity orderEntity = this.orderRepository.getReferenceById(id);
        List<OrderItemEntity> orderItemsEntities = this.orderItemRepository.findByOrderId(orderEntity.getId());

        return OrderMapper.toDomain(orderEntity, orderItemsEntities);
    }

    @Override
    public List<Order> findByStatus(OrderStatus orderStatus) {
        List<OrderEntity> orderEntities = this.orderRepository.findByOrderStatus(orderStatus);

        return mapToOrdersWithItems(orderEntities);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        List<OrderEntity> orderEntities = this.orderRepository.findByUser(userEntity);

        return mapToOrdersWithItems(orderEntities);
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

    private List<Order> mapToOrdersWithItems(List<OrderEntity> orderEntities) {
        if (orderEntities == null) {
            return null;
        }

        return orderEntities.stream()
                .map(orderEntity -> {
                    List<OrderItemEntity> orderItemsEntities = this.orderItemRepository.findByOrderId(orderEntity.getId());
                    return OrderMapper.toDomain(orderEntity, orderItemsEntities);
                })
                .collect(Collectors.toList());
    }
}
