package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Category;

import java.util.List;

public interface CategoryRepositoryPort {

   Category insertCategory(Category category);

   Category updateCategory(Long categoryId, Category category);

   Category getCategoryByDescription(String description);

   Category getCategoryById(Long id);

   List<Category> getAllCategories();
}
