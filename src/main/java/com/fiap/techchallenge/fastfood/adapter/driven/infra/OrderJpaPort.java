package com.fiap.techchallenge.fastfood.adapter.driven.infra;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderItemEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.OrderItemMapper;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.OrderMapper;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.OrderItemRepository;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.OrderRepository;
import com.fiap.techchallenge.fastfood.core.applications.ports.OrderRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderJpaPort implements OrderRepositoryPort {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Order generateOrder(Order order) {
        OrderEntity orderEntity = OrderMapper.toEntity(order);
        List<OrderItemEntity> orderItemEntities = order.getOrderItems().stream().map(OrderItemMapper::toEntity)
                .peek(x -> x.setOrder(orderEntity)).toList();
        orderEntity.setOrderItems(orderItemEntities);
        OrderEntity createdOrder = this.orderRepository.save(orderEntity);

        return OrderMapper.toDomain(createdOrder);
    }

    @Override
    public Order findById(Long id) {
        OrderEntity orderEntity = this.orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        List<OrderItemEntity> orderItemsEntities = this.orderItemRepository.findByOrderId(orderEntity.getId());

        return OrderMapper.toDomain(orderEntity, orderItemsEntities);
    }

    @Override
    public List<Order> findOrdersByQueryParams(Specification<OrderEntity> filters) {
        Specification<OrderEntity> spec = filters.and((root, query, criteriaBuilder) -> 
            criteriaBuilder.notEqual(root.get("orderStatus"), OrderStatus.DELIVERED));
    
        spec = spec.and((root, query, criteriaBuilder) -> {
            query.orderBy(
                criteriaBuilder.asc(
                    criteriaBuilder.selectCase(root.get("orderStatus"))
                        .when(OrderStatus.READY, 1)
                        .when(OrderStatus.IN_PREPARATION, 2)
                        .when(OrderStatus.PAID, 3)
                ),
                criteriaBuilder.asc(root.get("createdAt"))
            );
            return query.getRestriction();
        });
    
        List<OrderEntity> orderEntities = orderRepository.findAll(spec);
        return mapToOrdersWithItems(orderEntities);
    }
    

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus newOrderStatus) {
        OrderEntity orderEntity = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        orderEntity.setOrderStatus(newOrderStatus);

        this.orderRepository.save(orderEntity);
    }

    @Override
    public void updateOrderReference(Long orderId, String reference) {
        OrderEntity orderEntity = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        orderEntity.setReference(reference);

        this.orderRepository.save(orderEntity);
    }

    private List<Order> mapToOrdersWithItems(List<OrderEntity> orderEntities) {
        if (orderEntities == null) {
            return null;
        }

        return orderEntities.stream()
                .map(orderEntity -> {
                    List<OrderItemEntity> orderItemsEntities = this.orderItemRepository
                            .findByOrderId(orderEntity.getId());
                    return OrderMapper.toDomain(orderEntity, orderItemsEntities);
                })
                .collect(Collectors.toList());
    }
}
