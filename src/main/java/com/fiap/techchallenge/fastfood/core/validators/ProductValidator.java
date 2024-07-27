package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.ProductRepositoryPort;
import com.fiap.techchallenge.fastfood.core.exceptions.InvalidPriceException;
import com.fiap.techchallenge.fastfood.core.exceptions.ProductNotFoundInCategoryException;
import com.fiap.techchallenge.fastfood.core.exceptions.UserNotFoundException;

public class ProductValidator {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductValidator(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    public void validateProductExistsById(Long userId) {
        if (productRepositoryPort.findById(userId) == null) {
            throw new UserNotFoundException(userId);
        }
    }

    public void validateProductExistsByCategoryId(Long categoryId) {
        if (productRepositoryPort.findByCategoryId(categoryId) == null) {
            throw new ProductNotFoundInCategoryException(categoryId);
        }
    }

    public void validateProductPrice(Double price) {
        if(price < 0) {
            throw new InvalidPriceException(price);
        }
    }
}
