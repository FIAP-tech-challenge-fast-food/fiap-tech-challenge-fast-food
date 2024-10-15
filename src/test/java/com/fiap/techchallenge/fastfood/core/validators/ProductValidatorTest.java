package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.ProductRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import com.fiap.techchallenge.fastfood.core.exceptions.EmptyRequestBodyException;
import com.fiap.techchallenge.fastfood.core.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductValidatorTest {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @Mock
    private CategoryValidator categoryValidator;

    @InjectMocks
    private ProductValidator productValidator;

    private Category validCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validCategory = new Category(1L, "Bebidas");
    }

    private Product createValidProduct() {
        return new Product("Coca-cola 2L", "Refrigerante Cola-cola 2L", validCategory, 10.0);
    }

    @Test
    void testValidateProductExistsById_WithNullId_ShouldThrowException() {
        String expectedMessage = "productId cannot be empty";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productValidator.validateProductExistsById(null);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testValidateProductExistsById_WithNonExistentProduct_ShouldThrowProductNotFoundException() {
        Long productId = 1L;
        when(productRepositoryPort.findById(productId)).thenReturn(null);

        String expectedMessage = "Product not found with id: " + productId;
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productValidator.validateProductExistsById(productId);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testValidateProduct_WithNullProduct_ShouldThrowException() {
        String expectedMessage = "Request body cannot be empty";

        EmptyRequestBodyException exception = assertThrows(EmptyRequestBodyException.class, () -> {
            productValidator.validateProduct(null);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testValidateProduct_WithNullCategory_ShouldThrowException() {
        Product productWithNullCategory = createValidProduct();
        productWithNullCategory.setCategory(null);

        String expectedMessage = "Category cannot be empty";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productValidator.validateProduct(productWithNullCategory);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testValidateProduct_WithValidProduct_ShouldCallCategoryValidator() {
        Product validProduct = createValidProduct();

        productValidator.validateProduct(validProduct);

        verify(categoryValidator).validateCategoryExistsById(validCategory.getId());
    }
}
