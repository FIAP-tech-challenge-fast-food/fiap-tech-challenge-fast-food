package com.fiap.techchallenge.fastfood.adapter.driver.mappers;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.OrderItemDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.OrderItemRequest;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderItemMapperRequest {

    public static OrderItem toDomain(OrderItemRequest orderItemRequest) {
        if (orderItemRequest == null) {
            return null;
        }

        return new OrderItem(
                new Product(orderItemRequest.getProductId()),
                orderItemRequest.getQuantity());
    }

    public static List<OrderItem> mapToDomain(List<OrderItemRequest> items) {
        return items != null ?
                items.stream().map(OrderItemMapperRequest::toDomain).collect(Collectors.toList()) : new ArrayList<>();
    }
}
