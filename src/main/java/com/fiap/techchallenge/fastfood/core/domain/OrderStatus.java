package com.fiap.techchallenge.fastfood.core.domain;

public enum OrderStatus {
    WAITING_PAYMENT(0),
    PAID(1),
    IN_PREPARATION(2),
    READY(3),
    DELIVERED(4),
    CANCELED(5);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static boolean isValid(int value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getValue() == value) {
                return true;
            }
        }
        return false;
    }
}
