package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.ProductRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import com.fiap.techchallenge.fastfood.core.exceptions.EmptyRequestBodyException;
import com.fiap.techchallenge.fastfood.core.exceptions.ProductNotFoundException;
import jakarta.persistence.EntityNotFoundException;

public class ProductValidator {

    private final ProductRepositoryPort productRepositoryPort;
    private final CategoryValidator categoryValidator;

    public ProductValidator(ProductRepositoryPort productRepositoryPort, CategoryValidator categoryValidator) {
        this.productRepositoryPort = productRepositoryPort;
        this.categoryValidator = categoryValidator;
    }

    public void validateProductExistsById(Long productId) {
        try {
            if (productRepositoryPort.findById(productId) == null) {
                throw new ProductNotFoundException(productId);
            }
        } catch (EntityNotFoundException e) {
            throw new ProductNotFoundException(productId);
        }
    }

    public void validateProduct(Product product) {
        if(product == null) {
            throw new EmptyRequestBodyException("Request body cannot be empty");
        }

        if(product.getCategory() == null ) {
            throw new IllegalArgumentException("Category cannot be empty");
        }

        Long categoryId = product.getCategory().getId();
        if(categoryId == null) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        this.categoryValidator.validateCategoryExistsById(categoryId);
    }
}
