package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.exceptions.CategoryAlreadyExistsException;
import com.fiap.techchallenge.fastfood.core.exceptions.CategoryNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryValidatorTest {
    @Test
    void givenExistingCategory_whenValidateCategoryDescriptionAlreadyExists_thenThrowException() {
        // Given
        CategoryRepositoryPort categoryRepository = mock(CategoryRepositoryPort.class);
        when(categoryRepository.getCategoryByDescription(anyString())).thenReturn(new Category("Test"));
        CategoryValidator categoryValidator = new CategoryValidator(categoryRepository);

        // When and Then
        assertThrows(CategoryAlreadyExistsException.class, () -> categoryValidator.validateCategoryDescriptionAlreadyExists("Test"));
    }

    @Test
    void givenNonExistingCategory_whenValidateCategoryDescriptionAlreadyExists_thenNoException() {
        // Given
        CategoryRepositoryPort categoryRepository = mock(CategoryRepositoryPort.class);
        when(categoryRepository.getCategoryByDescription(anyString())).thenReturn(null);
        CategoryValidator categoryValidator = new CategoryValidator(categoryRepository);

        // When
        categoryValidator.validateCategoryDescriptionAlreadyExists("Test");

        // Then
        // No exception should be thrown
    }

    @Test
    void givenNonExistingCategory_whenValidateCategoryExistsById_thenThrowException() {
        // Given
        CategoryRepositoryPort categoryRepository = mock(CategoryRepositoryPort.class);
        Long categoryId = 1L;
        when(categoryRepository.getCategoryById(categoryId)).thenReturn(null);
        CategoryValidator categoryValidator = new CategoryValidator(categoryRepository);

        // When and Then
        assertThrows(CategoryNotFoundException.class, () -> categoryValidator.validateCategoryExistsById(categoryId));
    }

    @Test
    void givenExistingCategory_whenValidateCategoryExistsById_thenNoException() {
        // Given
        CategoryRepositoryPort categoryRepository = mock(CategoryRepositoryPort.class);
        Long categoryId = 1L;
        Category category = new Category(categoryId, "Test");
        when(categoryRepository.getCategoryById(categoryId)).thenReturn(category);
        CategoryValidator categoryValidator = new CategoryValidator(categoryRepository);

        // When
        categoryValidator.validateCategoryExistsById(categoryId);

        // Then
        // No exception should be thrown
    }
}
