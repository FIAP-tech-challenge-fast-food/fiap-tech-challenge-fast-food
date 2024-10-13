package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.domain.Payment;
import com.fiap.techchallenge.fastfood.core.domain.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentValidatorTest {
    private PaymentValidator paymentValidator;

    @BeforeEach
    void setUp() {
        this.paymentValidator = new PaymentValidator();
    }

    @Test
    public void givenNullPayment_whenValidate_thenThrowsException() {
        // Given
        Payment payment = null;

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> PaymentValidator.validate(payment));
    }

    @Test
    public void givenNonNullPayment_whenValidate_thenNoException() {
        // Given
        Payment payment = new Payment();

        // When
        PaymentValidator.validate(payment);

        // Then
        // No exception is thrown
    }

    @Test
    public void givenInvalidPaymentStatus_whenValidatePaymentStatus_thenThrowsException() {
        // Given
        String paymentStatus = "INVALID_STATUS";

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> this.paymentValidator.validatePaymentStatus(paymentStatus));
    }

    @Test
    public void givenApprovedPaymentStatus_whenValidatePaymentStatus_thenNoException() {
        // Given
        String paymentStatus = PaymentStatus.APPROVED.name();

        // When
        this.paymentValidator.validatePaymentStatus(paymentStatus);

        // Then
        // No exception is thrown
    }

    @Test
    public void givenRefusedPaymentStatus_whenValidatePaymentStatus_thenNoException() {
        // Given
        String paymentStatus = PaymentStatus.REFUSED.name();

        // When
        this.paymentValidator.validatePaymentStatus(paymentStatus);

        // Then
        // No exception is thrown
    }
}
