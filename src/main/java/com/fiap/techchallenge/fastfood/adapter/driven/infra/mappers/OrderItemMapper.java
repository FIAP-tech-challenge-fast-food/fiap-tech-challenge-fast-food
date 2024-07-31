package com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderItemEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.ProductEntity;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.Product;

public class OrderItemMapper {

     public static OrderItem toDomain(OrderItemEntity orderItemEntity) {
        if (orderItemEntity == null) {
            return null;
        }

        Product product = null;
        Order order = null;

        if (orderItemEntity.getProduct() != null) {
            product = ProductMapper.toDomain(orderItemEntity.getProduct());
        }

        if (orderItemEntity.getOrder() != null) {
            order = OrderMapper.toDomain(orderItemEntity.getOrder(), null);
        }

        return new OrderItem(orderItemEntity.getId(), 
                             product, 
                             order, 
                             orderItemEntity.getPrice(), 
                             orderItemEntity.getQuantity());
    }

    public static OrderItemEntity toEntity(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        
        ProductEntity product = null;
        OrderEntity order = null;

        if (orderItem.getProduct() != null) {
            product = ProductMapper.toEntity(orderItem.getProduct());
        }

        if (orderItem.getOrder() != null) {
            order = OrderMapper.toEntity(orderItem.getOrder());
        }

        return new OrderItemEntity(orderItem.getId(), 
                                   product, 
                                   order, 
                                   orderItem.getPrice(), 
                                   orderItem.getQuantity());
    }

}
