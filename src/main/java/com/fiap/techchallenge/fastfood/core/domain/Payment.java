package com.fiap.techchallenge.fastfood.core.domain;

import java.util.Date;

public class Payment {

    private Long id;

    private String externalReference;

    private Order order;

    private Date createdAt;

    public Payment() {
    }

    public Payment(Long id, String externalReference, Order order, Date createdAt) {
        this.id = id;
        this.externalReference = externalReference;
        this.order = order;
        this.createdAt = createdAt;
    }

    public Payment(String externalReference, Order order, Date createdAt) {
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
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
