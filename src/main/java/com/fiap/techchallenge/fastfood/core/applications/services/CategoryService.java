package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CategoryService implements CategoryServicePort {

    private final CategoryRepositoryPort categoryRepositoryPort;

    public void insertCategory(Category category) {
        this.categoryRepositoryPort.insertCategory(category);
    }

    public void updateCategory(Long categoryId, Category category) {
        this.categoryRepositoryPort.updateCategory(categoryId, category);
    }

    public Category getCategoryById(Long id) {
        return this.categoryRepositoryPort.getCategoryById(id);
    }

    public List<Category> getAllCategories() {
        return this.categoryRepositoryPort.getAllCategories();
    }

}
