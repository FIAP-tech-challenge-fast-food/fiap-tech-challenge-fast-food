package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.exceptions.CategoryNotFoundException;
import jakarta.persistence.EntityNotFoundException;

public class CategoryValidator {

    private CategoryRepositoryPort categoryRepositoryPort;

    public CategoryValidator(CategoryRepositoryPort categoryRepositoryPort) {
        this.categoryRepositoryPort = categoryRepositoryPort;
    }

    public void validateCategoryExists(Long categoryId) {
        Category category;
        try {
            category = this.categoryRepositoryPort.getCategoryById(categoryId);
        } catch (EntityNotFoundException e) {
            throw new CategoryNotFoundException(categoryId);
        }
        if (category == null) {
            throw new CategoryNotFoundException(categoryId);
        }
    }
}
