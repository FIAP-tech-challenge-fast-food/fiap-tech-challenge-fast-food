package com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.OrderEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.UserEntity;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>, JpaSpecificationExecutor<OrderEntity> {

    List<OrderEntity> findByOrderStatus(OrderStatus orderStatus);
    
    List<OrderEntity> findByUser(UserEntity user);

    List<OrderEntity> findByOrderStatusAndUserId(OrderStatus orderStatus, Long userId);

}