package com.fiap.techchallenge.fastfood.core.applications.services.validation;

public class MiscValidator {

    public static void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
    }

}
