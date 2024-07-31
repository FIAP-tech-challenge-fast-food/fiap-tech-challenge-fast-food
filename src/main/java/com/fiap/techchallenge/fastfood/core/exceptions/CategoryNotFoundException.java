package com.fiap.techchallenge.fastfood.core.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Category not found for id: " + id);
    }

    public CategoryNotFoundException(String param) {
        super("Category not found");
    }
}
