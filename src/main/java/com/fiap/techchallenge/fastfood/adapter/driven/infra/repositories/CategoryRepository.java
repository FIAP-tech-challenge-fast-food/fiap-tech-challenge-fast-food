package com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
