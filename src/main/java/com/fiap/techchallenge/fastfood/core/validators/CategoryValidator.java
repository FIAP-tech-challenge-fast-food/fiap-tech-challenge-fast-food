package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryRepositoryPort;
import com.fiap.techchallenge.fastfood.core.exceptions.CategoryAlreadyExistsException;
import com.fiap.techchallenge.fastfood.core.exceptions.CategoryNotFoundException;

public class CategoryValidator {

    private final CategoryRepositoryPort categoryRepositoryPort;

    public CategoryValidator(CategoryRepositoryPort categoryRepositoryPort) {
        this.categoryRepositoryPort = categoryRepositoryPort;
    }

    public void validateCategoryDescriptionAlreadyExists(String desc) {
        if (categoryRepositoryPort.getCategoryByDescription(desc) != null) {
            throw new CategoryAlreadyExistsException(desc);
        }
    }

    public void validateCategoryExistsById(Long id) {
        if (categoryRepositoryPort.getCategoryById(id) == null) {
            throw new CategoryNotFoundException(id);
        }
    }
}
