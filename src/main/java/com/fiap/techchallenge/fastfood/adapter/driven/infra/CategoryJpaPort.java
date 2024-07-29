package com.fiap.techchallenge.fastfood.adapter.driven.infra;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.CategoryEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.CategoryMapper;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.CategoryRepository;
import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryJpaPort implements CategoryRepositoryPort {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void insertCategory(Category category) {
        this.categoryRepository.save(CategoryMapper.toEntity(category));
    }

    @Override
    public void updateCategory(Long categoryId, Category category) {
        this.categoryRepository.save(CategoryMapper.toEntity(category));
    }

    @Override
    public Category getCategoryById(Long id) {
        CategoryEntity categoryEntity = this.categoryRepository.getReferenceById(id);
        return CategoryMapper.toDomain(categoryEntity);
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll().stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }
}
