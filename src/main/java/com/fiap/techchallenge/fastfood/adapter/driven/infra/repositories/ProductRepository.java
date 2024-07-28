package com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.CategoryEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    
    List<ProductEntity> findByCategory(CategoryEntity categoryEntity);
}
