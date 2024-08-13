package com.fiap.techchallenge.fastfood.adapter.driver.mappers;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.CategoryDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.CreateCategoryRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.UpdateCategoryRequest;
import com.fiap.techchallenge.fastfood.core.domain.Category;

public class CategoryMapperDto {

    public static CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryDto(
                category.getId(),
                category.getDescription());
    }

    public static Category toDomain(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }

        return createCategoryWithDescription(categoryDto.getDescription());
    }

    public static Category toDomain(CreateCategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            return null;
        }

        return createCategoryWithDescription(categoryRequest.getDescription());
    }

    public static Category toDomain(UpdateCategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            return null;
        }

        return createCategoryWithDescription(categoryRequest.getDescription());
    }

    private static Category createCategoryWithDescription(String description) {
        return new Category(description);
    }
}
