package com.fiap.techchallenge.fastfood.core.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String desc) {
        super("Category already exists: " + desc);
    }
}
