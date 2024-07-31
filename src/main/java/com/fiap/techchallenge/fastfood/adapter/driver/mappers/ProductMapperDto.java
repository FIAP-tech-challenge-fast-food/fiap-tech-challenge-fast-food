package com.fiap.techchallenge.fastfood.adapter.driver.mappers;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.CategoryDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.ProductDto;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import com.fiap.techchallenge.fastfood.core.domain.Category;

public class ProductMapperDto {

    public static Product toDomain(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        return new Product(
                productDto.getName(),
                productDto.getDescription(),
                new Category(productDto.getCategoryId()),
                productDto.getPrice());
    }

    public static ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        Long categoryId = null;
        if (product.getCategory() != null) {
            categoryId = product.getCategory().getId();
        }

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                categoryId,
                product.getPrice());
    }
}
