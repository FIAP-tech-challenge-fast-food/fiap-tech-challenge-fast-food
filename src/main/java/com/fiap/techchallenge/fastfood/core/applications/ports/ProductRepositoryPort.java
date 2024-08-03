package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.domain.Product;

import java.util.List;

public interface ProductRepositoryPort {

    Product register(Product product);

    Product update(Product product);

    Product findById(Long id);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findAll();

    void remove(Long productId);
}
