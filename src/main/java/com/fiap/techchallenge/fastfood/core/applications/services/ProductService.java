package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.ProductRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService implements ProductServicePort {

    @Autowired
    private ProductRepositoryPort productRepositoryPort;

    public void register(String name, String description, Category category, Double price) {
        this.productRepositoryPort.register(name, description, category, price);
    }

    public void update(String name, String description, Category category, Double price) {
        this.productRepositoryPort.update(name, description, category, price);
    }

    public Product findById(Long id) {
        return this.productRepositoryPort.findById(id);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return this.productRepositoryPort.findByCategoryId(categoryId);
    }

}
