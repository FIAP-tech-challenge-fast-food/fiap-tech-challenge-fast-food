package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.OrderRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import com.fiap.techchallenge.fastfood.core.exceptions.InvalidOrderStatusException;
import com.fiap.techchallenge.fastfood.core.exceptions.OrderNotFoundException;
import com.fiap.techchallenge.fastfood.core.exceptions.UpdateOrderStatusException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderValidatorTest {

    @Test
    public void testValidateOrderExistsById() {
        // Given
        OrderRepositoryPort orderRepositoryPort = Mockito.mock(OrderRepositoryPort.class);
        OrderItemValidator orderItemValidator = Mockito.mock(OrderItemValidator.class);
        UserValidator userValidator = Mockito.mock(UserValidator.class);
        OrderValidator orderValidator = new OrderValidator(orderRepositoryPort, orderItemValidator, userValidator);
        Long orderId = 1L;

        // When
        Mockito.when(orderRepositoryPort.findById(orderId)).thenReturn(new Order(orderId));
        orderValidator.validateOrderExistsById(orderId);

        // Then
        // No exception is thrown
    }

    @Test
    public void testValidateOrderExistsById_InvalidOrderId() {
        // Given
        OrderRepositoryPort orderRepositoryPort = Mockito.mock(OrderRepositoryPort.class);
        OrderItemValidator orderItemValidator = Mockito.mock(OrderItemValidator.class);
        UserValidator userValidator = Mockito.mock(UserValidator.class);
        OrderValidator orderValidator = new OrderValidator(orderRepositoryPort, orderItemValidator, userValidator);

        Long orderId = 1L;
        Mockito.when(orderRepositoryPort.findById(orderId)).thenReturn(null);

        // When and Then
        assertThrows(OrderNotFoundException.class, () -> orderValidator.validateOrderExistsById(orderId));
    }

    @Test
    public void givenNewOrderStatusInPreparation_whenValidateOrderCanChangeStatusPaid_thenNoException() {
        // Given
        OrderRepositoryPort orderRepositoryPort = Mockito.mock(OrderRepositoryPort.class);
        OrderItemValidator orderItemValidator = Mockito.mock(OrderItemValidator.class);
        UserValidator userValidator = Mockito.mock(UserValidator.class);
        OrderValidator orderValidator = new OrderValidator(orderRepositoryPort, orderItemValidator, userValidator);

        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.IN_PREPARATION;

        // When
        Order existingOrder = new Order(orderId);
        existingOrder.setOrderStatus(OrderStatus.PAID);
        Mockito.when(orderRepositoryPort.findById(orderId)).thenReturn(existingOrder);
        orderValidator.validateOrderCanChangeStatusTo(orderId, newStatus);

        // Then
        // No exception is thrown
    }


    @Test
    public void givenNewOrderStatusInPreparation_whenValidateOrderCanChangeStatusReady_thenNoException() {
        // Given
        OrderRepositoryPort orderRepositoryPort = Mockito.mock(OrderRepositoryPort.class);
        OrderItemValidator orderItemValidator = Mockito.mock(OrderItemValidator.class);
        UserValidator userValidator = Mockito.mock(UserValidator.class);
        OrderValidator orderValidator = new OrderValidator(orderRepositoryPort, orderItemValidator, userValidator);

        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.READY;

        // When
        Order existingOrder = new Order(orderId);
        existingOrder.setOrderStatus(OrderStatus.IN_PREPARATION);
        Mockito.when(orderRepositoryPort.findById(orderId)).thenReturn(existingOrder);
        orderValidator.validateOrderCanChangeStatusTo(orderId, newStatus);

        // Then
        // No exception is thrown
    }

    @Test
    public void givenNewOrderStatusReady_whenValidateOrderCanChangeStatusDelivered_thenNoException() {
        // Given
        OrderRepositoryPort orderRepositoryPort = Mockito.mock(OrderRepositoryPort.class);
        OrderItemValidator orderItemValidator = Mockito.mock(OrderItemValidator.class);
        UserValidator userValidator = Mockito.mock(UserValidator.class);
        OrderValidator orderValidator = new OrderValidator(orderRepositoryPort, orderItemValidator, userValidator);

        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.DELIVERED;

        // When
        Order order = new Order(orderId);
        order.setOrderStatus(OrderStatus.READY);
        Mockito.when(orderRepositoryPort.findById(orderId)).thenReturn(order);
        orderValidator.validateOrderCanChangeStatusTo(orderId, newStatus);

        // Then
        // No exception is thrown
    }

    @Test
    public void givenNewOrderStatus_whenValidateOrderCanChangeStatusIsTheSame_thenThrowException() {
        // Given
        OrderRepositoryPort orderRepositoryPort = Mockito.mock(OrderRepositoryPort.class);
        OrderItemValidator orderItemValidator = Mockito.mock(OrderItemValidator.class);
        UserValidator userValidator = Mockito.mock(UserValidator.class);
        OrderValidator orderValidator = new OrderValidator(orderRepositoryPort, orderItemValidator, userValidator);

        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.IN_PREPARATION;

        // When
        Order existingOrder = new Order(orderId);
        existingOrder.setOrderStatus(OrderStatus.IN_PREPARATION);
        Mockito.when(orderRepositoryPort.findById(orderId)).thenReturn(existingOrder);

        // Then
        assertThrows(UpdateOrderStatusException.class, () -> orderValidator.validateOrderCanChangeStatusTo(orderId, newStatus));
    }

    @Test
    public void testValidateOrderStatusExists_InvalidStatus() {
        // Given
        OrderRepositoryPort orderRepositoryPort = Mockito.mock(OrderRepositoryPort.class);
        OrderItemValidator orderItemValidator = Mockito.mock(OrderItemValidator.class);
        UserValidator userValidator = Mockito.mock(UserValidator.class);
        OrderValidator orderValidator = new OrderValidator(orderRepositoryPort, orderItemValidator, userValidator);

        String orderStatus = "INVALID_STATUS";

        // When and Then
        assertThrows(InvalidOrderStatusException.class, () -> orderValidator.validateOrderStatusExists(orderStatus));
    }
}
