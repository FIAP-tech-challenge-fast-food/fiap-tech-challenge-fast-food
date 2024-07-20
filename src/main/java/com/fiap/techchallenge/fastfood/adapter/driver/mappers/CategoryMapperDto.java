package com.fiap.techchallenge.fastfood.adapter.driver.mappers;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.CategoryDto;
import com.fiap.techchallenge.fastfood.core.domain.Category;

public class CategoryMapperDto {

    public static Category toDomain(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }

        return new Category(
                categoryDto.getId(),
                categoryDto.getDescription());
    }

    public static CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryDto(
                category.getId(),
                category.getDescription());
    }
}
