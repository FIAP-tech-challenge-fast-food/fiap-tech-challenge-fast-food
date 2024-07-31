package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.validators.CategoryValidator;

import java.util.List;

public class CategoryService implements CategoryServicePort {

    private final CategoryRepositoryPort categoryRepositoryPort;

    private final CategoryValidator categoryValidator;

    public CategoryService(CategoryRepositoryPort categoryRepositoryPort) {
        this.categoryRepositoryPort = categoryRepositoryPort;
        this.categoryValidator = new CategoryValidator(categoryRepositoryPort);
    }

    public Category insertCategory(Category category) {
        categoryValidator.validateCategoryAlreadyExists(category.getDescription());
        return this.categoryRepositoryPort.insertCategory(category);
    }
    public Category updateCategory(Long id, Category category) {
        categoryValidator.validateCategoryExistsById(id);
        return this.categoryRepositoryPort.updateCategory(id, category);
    }

    public Category getCategoryByDescription(String description) {
        return this.categoryRepositoryPort.getCategoryByDescription(description);
    }

    public Category getCategoryById(Long id) {
        categoryValidator.validateCategoryExistsById(id);
        return this.categoryRepositoryPort.getCategoryById(id);
    }

    public List<Category> getAllCategories() {
        return this.categoryRepositoryPort.getAllCategories();
    }
}
