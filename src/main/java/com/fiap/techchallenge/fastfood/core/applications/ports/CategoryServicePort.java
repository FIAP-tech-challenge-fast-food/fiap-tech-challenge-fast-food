package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Category;

import java.util.List;

public interface CategoryServicePort {

   public void insertCategory(Category category);

   public void updateCategory(Long categoryId, Category category);

   public Category getCategoryById(Long id);

   public List<Category> getAllCategories();
}
