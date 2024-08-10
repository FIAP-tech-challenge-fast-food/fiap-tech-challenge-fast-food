package com.fiap.techchallenge.fastfood.core.domain;

public enum OrderStatus {
    WAITING_PAYMENT(0),
    PAID(1),
    IN_PREPARATION(2),
    READY(3),
    DELIVERED(4),
    CANCELED(5);

    private final int value;

    public int getValue() {
        return value;
    }

    OrderStatus(int value) {
        this.value = value;
    }

    public static boolean isValid(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        try {
            OrderStatus.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
