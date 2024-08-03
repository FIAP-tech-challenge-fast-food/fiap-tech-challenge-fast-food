package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Product;

import java.util.List;

public interface ProductServicePort {

    Product register(Product product);

    Product update(Product product, Long productId);

    Product findById(Long id);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findAll();

    void remove(Long productId);
}
