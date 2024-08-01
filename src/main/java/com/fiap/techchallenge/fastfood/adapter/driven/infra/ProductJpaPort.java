package com.fiap.techchallenge.fastfood.adapter.driven.infra;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.CategoryEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.ProductEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.ProductMapper;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.ProductRepository;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
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
    public void update(String name, String description, Category category, Double price) {
        this.productRepository.save(ProductMapper.toEntity(new Product(name, description, category, price)));
    }

    @Override
    public Product findById(Long id) {
        ProductEntity productEntity = this.productRepository.getReferenceById(id);
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);
        var productEntities = this.productRepository.findByCategory(categoryEntity);

        return productEntities.stream().map(ProductMapper::toDomain).collect(Collectors.toList());
    }
}
