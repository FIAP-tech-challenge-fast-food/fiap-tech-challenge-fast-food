package com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.CategoryEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.ProductEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ProductRepository extends ListCrudRepository<ProductEntity, Long> {
    
    List<ProductEntity> findByCategory(CategoryEntity categoryEntity);
}
