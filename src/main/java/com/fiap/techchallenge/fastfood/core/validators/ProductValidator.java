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

    public Product validateProductExistsById(Long productId) {
        Product product;
        if(productId == null) {
            throw new IllegalArgumentException("productId cannot be empty");
        }
        try {
            product = productRepositoryPort.findById(productId);
            if (product == null) {
                throw new ProductNotFoundException(productId);
            }

            return product;
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
        if(categoryId != null) {
            this.categoryValidator.validateCategoryExistsById(categoryId);
        }
    }
}
