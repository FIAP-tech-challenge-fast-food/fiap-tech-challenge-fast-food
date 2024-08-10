package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import com.fiap.techchallenge.fastfood.core.validators.CategoryValidator;
import com.fiap.techchallenge.fastfood.core.validators.ProductValidator;

import java.util.List;

public class ProductService implements ProductServicePort {

    private final ProductRepositoryPort productRepositoryPort;
    private final ProductValidator productValidator;

    public ProductService(ProductRepositoryPort productRepositoryPort, CategoryRepositoryPort categoryRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
        this.productValidator = new ProductValidator(productRepositoryPort, new CategoryValidator(categoryRepositoryPort));
    }

    @Override
    public Product register(Product product) {
        this.productValidator.validateProduct(product);
        return this.productRepositoryPort.register(
                new Product(product.getName(),
                        product.getDescription(),
                        product.getCategory(),
                        product.getPrice()));
    }

    @Override
    public Product update(Product product, Long productId) {
        this.productValidator.validateProductExistsById(productId);
        this.productValidator.validateProduct(product);
        product.setId(productId);
        return this.productRepositoryPort.update(product);
    }

    @Override
    public Product findById(Long id) {
        productValidator.validateProductExistsById(id);
        return this.productRepositoryPort.findById(id);
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return this.productRepositoryPort.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> findAll() {
        return this.productRepositoryPort.findAll();
    }

    @Override
    public void remove(Long productId) {
        productValidator.validateProductExistsById(productId);
        this.productRepositoryPort.remove(productId);
    }
}
