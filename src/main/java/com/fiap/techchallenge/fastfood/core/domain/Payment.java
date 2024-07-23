package com.fiap.techchallenge.fastfood.core.domain;

import java.time.LocalDateTime;

public class Payment {

    private Long id;

    private String externalReference;

    private Order order;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(Long id, String externalReference, Order order, LocalDateTime createdAt) {
        this.id = id;
        this.externalReference = externalReference;
        this.order = order;
        this.createdAt = createdAt;
    }

    public Payment(String externalReference, Order order, LocalDateTime createdAt) {
        this.externalReference = externalReference;
        this.order = order;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getExternalReference() {
        return externalReference;
    }
    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
