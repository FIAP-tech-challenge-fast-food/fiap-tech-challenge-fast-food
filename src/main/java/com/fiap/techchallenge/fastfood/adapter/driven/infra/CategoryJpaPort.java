package com.fiap.techchallenge.fastfood.adapter.driven.infra;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.CategoryEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.CategoryMapper;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.CategoryRepository;
import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.exceptions.CategoryNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryJpaPort implements CategoryRepositoryPort {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category insertCategory(Category category) {
        CategoryEntity categoryEntity = this.categoryRepository.save(CategoryMapper.toEntity(category));
        return CategoryMapper.toDomain(categoryEntity);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        CategoryEntity categoryEntity = this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        categoryEntity.setDescription(category.getDescription());
        this.categoryRepository.save(categoryEntity);

        return CategoryMapper.toDomain(categoryEntity);
    }

    @Override
    public Category getCategoryByDescription(String description) {
        CategoryEntity categoryEntity = this.categoryRepository.findByDescription(description).orElse(null);
        return categoryEntity != null ? CategoryMapper.toDomain(categoryEntity) : null;
    }

    @Override
    public Category getCategoryById(Long id) {
        CategoryEntity categoryEntity = this.categoryRepository.findById(id).orElse(null);
        return categoryEntity != null ? CategoryMapper.toDomain(categoryEntity) : null;
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll().stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }
}
