package com.fiap.techchallenge.fastfood.core.domain;

public enum OrderStatus {
    WAITING_PAYMENT(1),
    PAID(2),
    IN_PREPARATION(3),
    READY(4),
    DELIVERED(5),
    CANCELED(6);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
