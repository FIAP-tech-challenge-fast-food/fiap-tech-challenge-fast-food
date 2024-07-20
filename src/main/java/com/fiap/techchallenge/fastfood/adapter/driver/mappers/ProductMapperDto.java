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

        Category category = null;
        if (productDto.getCategory() != null) {
            category = new Category(
                    productDto.getCategory().getId(),
                    productDto.getCategory().getDescription());
        }

        return new Product(
                productDto.getId(),
                productDto.getName(),
                category,
                productDto.getPrice());
    }

    public static ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        CategoryDto categoryDto = null;
        if (product.getCategory() != null) {
            categoryDto = new CategoryDto(
                    product.getCategory().getId(),
                    product.getCategory().getDescription());
        }

        return new ProductDto(
                product.getId(),
                product.getName(),
                categoryDto,
                product.getPrice());
    }
}
