package com.fiap.techchallenge.fastfood.core.applications.ports;

import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.domain.Product;

import java.util.List;

public interface ProductServicePort {

    public Product register(Product product);

    public void update(String name, String description, Category category, Double price);

    public Product findById(Long id);

    public List<Product> findByCategoryId(Long categoryId);
}
