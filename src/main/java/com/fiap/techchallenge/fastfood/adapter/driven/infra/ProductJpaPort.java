package com.fiap.techchallenge.fastfood.adapter.driven.infra;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.CategoryEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.ProductEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.ProductMapper;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.ProductRepository;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductJpaPort implements ProductRepositoryPort {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product register(Product product) {
        ProductEntity productEntity = this.productRepository.save(ProductMapper.toEntity(product));
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public Product update(Product product) {
        ProductEntity productEntity = this.productRepository.save(ProductMapper.toEntity(product));
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public Product findById(Long id) {
        ProductEntity productEntity = this.productRepository.findById(id).orElse(null);
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);
        var productEntities = this.productRepository.findByCategory(categoryEntity);

        return productEntities.stream().map(ProductMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Product> findAll() {
        var productEntities = this.productRepository.findAll();
        return productEntities.stream().map(ProductMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void remove(Long productId) {
        this.productRepository.deleteById(productId);
    }
}
