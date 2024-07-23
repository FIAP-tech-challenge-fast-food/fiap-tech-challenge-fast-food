package com.fiap.techchallenge.fastfood.adapter.driven.infra;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderItemEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.OrderItemMapper;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.OrderItemRepository;
import com.fiap.techchallenge.fastfood.core.applications.ports.OrderItemRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.Product;

@Component
public class OrderItemJpaPort implements OrderItemRepositoryPort{

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void register(Product product, Order order, Double price, Integer quantity) {
        this.orderItemRepository.save(OrderItemMapper.toEntity(new OrderItem(product, order, price, quantity)));
    }
    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        List<OrderItemEntity> orderItemEntities = this.orderItemRepository.findByOrderId(orderId);

        return orderItemEntities.stream().map(OrderItemMapper::toDomain).collect(Collectors.toList());
    }

}
