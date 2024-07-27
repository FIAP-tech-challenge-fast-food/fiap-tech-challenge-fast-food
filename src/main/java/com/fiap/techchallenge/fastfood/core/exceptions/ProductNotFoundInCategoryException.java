package com.fiap.techchallenge.fastfood.core.exceptions;

public class ProductNotFoundInCategoryException extends RuntimeException {
    public ProductNotFoundInCategoryException(Long categoryId) {
        super("Product not found with category id: " + categoryId);
    }
}
