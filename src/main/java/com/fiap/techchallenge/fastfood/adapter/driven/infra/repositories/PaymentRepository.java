package com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
  
    PaymentEntity findByOrderId(Long orderId);

    PaymentEntity findByExternalReference(String externalReference);
}
