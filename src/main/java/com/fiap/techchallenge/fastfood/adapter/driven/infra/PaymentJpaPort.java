package com.fiap.techchallenge.fastfood.adapter.driven.infra;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.PaymentEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.PaymentMapper;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.PaymentRepository;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

@Component
public class PaymentJpaPort implements PaymentRepositoryPort {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment registerPayment(Payment payment) {
        PaymentEntity paymentEntity = this.paymentRepository.save(PaymentMapper.toEntity(payment));

        return PaymentMapper.toDomain(paymentEntity);
    }

    @Override
    public List<Payment> findAll() {
        List<PaymentEntity> paymentEntities = this.paymentRepository.findAll();

        return paymentEntities.stream()
                .map(PaymentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Payment findByOrderId(Long orderId) {
        PaymentEntity paymentEntity = this.paymentRepository.findByOrderId(orderId);

        return PaymentMapper.toDomain(paymentEntity);
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = this.paymentRepository.save(PaymentMapper.toEntity(payment));

        return PaymentMapper.toDomain(paymentEntity);
    }

    @Override
    public Payment findByExternalReference(String externalReference) {
        PaymentEntity paymentEntity = this.paymentRepository.findByExternalReference(externalReference);

        return PaymentMapper.toDomain(paymentEntity);
    }
}
