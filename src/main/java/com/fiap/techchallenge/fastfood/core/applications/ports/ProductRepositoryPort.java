package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.domain.Product;

import java.util.List;

public interface ProductRepositoryPort {

    Product register(Product product);

    void update(String name, String description, Category category, Double price);

    Product findById(Long id);

    List<Product> findByCategoryId(Long categoryId);
}
