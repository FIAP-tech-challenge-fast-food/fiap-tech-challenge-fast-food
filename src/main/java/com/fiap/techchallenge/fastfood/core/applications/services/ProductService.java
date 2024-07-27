package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.ProductRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import com.fiap.techchallenge.fastfood.core.validators.ProductValidator;

import java.util.List;

public class ProductService implements ProductServicePort {

    private final ProductRepositoryPort productRepositoryPort;

    private final ProductValidator productValidator;

    public ProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
        this.productValidator = new ProductValidator(productRepositoryPort);
    }

    public Product register(Product product) {
        productValidator.validateProductPrice(product.getPrice());
        return this.productRepositoryPort.register(
                new Product(product.getName(),
                        product.getDescription(),
                        product.getCategory(),
                        product.getPrice()));
    }

    public void update(String name, String description, Category category, Double price) {
        this.productRepositoryPort.update(name, description, category, price);
    }

    public Product findById(Long id) {
        productValidator.validateProductExistsById(id);
        return this.productRepositoryPort.findById(id);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        productValidator.validateProductExistsByCategoryId(categoryId);
        return this.productRepositoryPort.findByCategoryId(categoryId);
    }
}
