package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.domain.Category;
import com.fiap.techchallenge.fastfood.core.domain.OrderItem;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderItemValidatorTest {

    private final OrderItemValidator orderItemValidator = new OrderItemValidator();

    private OrderItem createValidOrderItem() {
        Category category = new Category(1L, "Bebidas");
        Product product = new Product(1L, "Coca-cola 2L", "Refrigerante Cola-cola 2L", category, 10.0);

        return new OrderItem(product, 1);
    }

    @Test
    void testValidate_WithNullOrderItem_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderItemValidator.validate(null);
        });
        assertEquals("Order item cannot be null", exception.getMessage());
    }

    @Test
    void testValidate_WithNullProduct_ShouldThrowException() {
        OrderItem orderItem = createValidOrderItem();
        orderItem.setProduct(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderItemValidator.validate(orderItem);
        });
        assertEquals("Product in order item cannot be null", exception.getMessage());
    }

    @Test
    void testValidate_WithNullProductId_ShouldThrowException() {
        OrderItem orderItem = createValidOrderItem();
        orderItem.getProduct().setId(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderItemValidator.validate(orderItem);
        });
        assertEquals("Product in order item cannot be null", exception.getMessage());
    }

    @Test
    void testValidate_WithInvalidProductId_ShouldThrowException() {
        OrderItem orderItem = createValidOrderItem();
        orderItem.getProduct().setId(0L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderItemValidator.validate(orderItem);
        });
        assertEquals("Product ID in order item cannot be less than or equal to zero", exception.getMessage());
    }

    @Test
    void testValidate_WithNullQuantity_ShouldThrowException() {
        OrderItem orderItem = createValidOrderItem();
        orderItem.setQuantity(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderItemValidator.validate(orderItem);
        });
        assertEquals("Product quantity in order item cannot be null or less than or equal to zero", exception.getMessage());
    }

    @Test
    void testValidate_WithInvalidQuantity_ShouldThrowException() {
        OrderItem orderItem = createValidOrderItem();
        orderItem.setQuantity(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderItemValidator.validate(orderItem);
        });
        assertEquals("Product quantity in order item cannot be null or less than or equal to zero", exception.getMessage());
    }

    @Test
    void testValidateItems_WithNullList_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderItemValidator.validateItems(null);
        });
        assertEquals("Order items cannot be null or empty", exception.getMessage());
    }

    @Test
    void testValidateItems_WithEmptyList_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderItemValidator.validateItems(List.of());
        });
        assertEquals("Order items cannot be null or empty", exception.getMessage());
    }

    @Test
    void testValidateItems_WithValidItems_ShouldNotThrowException() {
        OrderItem validOrderItem = createValidOrderItem();
        orderItemValidator.validateItems(List.of(validOrderItem));
    }

    @Test
    void testValidateItems_WithInvalidItem_ShouldThrowException() {
        OrderItem invalidOrderItem = createValidOrderItem();
        invalidOrderItem.setQuantity(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderItemValidator.validateItems(List.of(invalidOrderItem));
        });
        assertEquals("Product quantity in order item cannot be null or less than or equal to zero", exception.getMessage());
    }
}
