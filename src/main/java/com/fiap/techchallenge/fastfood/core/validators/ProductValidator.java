package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.ProductRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import com.fiap.techchallenge.fastfood.core.exceptions.InvalidPriceException;
import com.fiap.techchallenge.fastfood.core.exceptions.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;

public class ProductValidator {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductValidator(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    public void validateProductExistsById(Long userId) {
        try {
            if (productRepositoryPort.findById(userId) == null) {
                throw new UserNotFoundException(userId);
            }
        } catch (EntityNotFoundException e) {
            throw new UserNotFoundException(userId);
        }
    }

}
