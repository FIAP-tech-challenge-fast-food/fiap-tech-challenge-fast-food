package com.fiap.techchallenge.fastfood.core.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long categoryNotFound) {
        super("Category not found for id: " + categoryNotFound);
    }
}
