package com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.ProductEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.CategoryEntity;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import com.fiap.techchallenge.fastfood.core.domain.Category;

public class ProductMapper {

    public static Product toDomain(ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }

        Category category = null;
        if (productEntity.getCategory() != null) {
            category = new Category(
                    productEntity.getCategory().getId(),
                    productEntity.getCategory().getDescription());
        }

        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                category,
                productEntity.getPrice());
    }

    public static ProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }

        CategoryEntity categoryEntity = null;
        if (product.getCategory() != null) {
            categoryEntity = new CategoryEntity(
                    product.getCategory().getId(),
                    product.getCategory().getDescription());
        }

        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                categoryEntity,
                product.getPrice(),
                false);
    }
}
