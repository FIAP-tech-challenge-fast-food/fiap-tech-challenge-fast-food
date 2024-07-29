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

    public void insertCategory(Category category) {
        this.categoryRepositoryPort.insertCategory(category);
    }

    public void updateCategory(Long categoryId, Category category) {
        this.categoryRepositoryPort.updateCategory(categoryId, category);
    }

    public Category getCategoryById(Long id) {
        this.categoryValidator.validateCategoryExists(id);
        return this.categoryRepositoryPort.getCategoryById(id);
    }

    public List<Category> getAllCategories() {
        return this.categoryRepositoryPort.getAllCategories();
    }
}
