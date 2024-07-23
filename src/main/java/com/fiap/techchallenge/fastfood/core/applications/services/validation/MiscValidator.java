package com.fiap.techchallenge.fastfood.core.applications.services.validation;

public class MiscValidator {

    public static void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
    }

}
