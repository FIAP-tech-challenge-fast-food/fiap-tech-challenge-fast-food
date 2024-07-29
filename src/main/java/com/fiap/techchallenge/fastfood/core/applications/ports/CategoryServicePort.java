package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Category;

import java.util.List;

public interface CategoryServicePort {

   public Category insertCategory(Category category);

   public Category updateCategory(Long id, Category category);

   public Category getCategoryById(Long id);

   public Category getCategoryByDescription(String description);

   public List<Category> getAllCategories();
}
