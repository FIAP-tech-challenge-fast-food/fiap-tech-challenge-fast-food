package com.fiap.techchallenge.fastfood.adapter.driver.controllers.api;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.PaymentConfirmationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.PaymentDto;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.PaymentMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;

import io.swagger.v3.oas.annotations.*;

@RestController
@RequestMapping(value = "payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payment Management", description = "Operations related to payment management")
public class PaymentController {

    @Autowired
    private PaymentServicePort paymentServicePort;

    @GetMapping
    @Operation(summary = "Get payments", description = "Retrieve a list of payments")
    @ApiResponse(responseCode = "200", description = "List of payments retrieved successfully")
    public ResponseEntity<List<PaymentDto>> findOrder(@Parameter(description = "ID of the order to retrieve payments for") @RequestParam(required = false) Long id) {
        List<PaymentDto> paymentDtos = new ArrayList<>();
        if (id == null) {
            List<Payment> payments = paymentServicePort.findAll();
            paymentDtos = payments.stream().map(PaymentMapperDto::toDto).collect(Collectors.toList());
        } else {
            Payment payment = paymentServicePort.findByOrderId(id);
            if(payment != null) {
                paymentDtos.add(PaymentMapperDto.toDto(payment));
            }
        }

        return ResponseEntity.ok(paymentDtos);
    }

    @PostMapping("/confirm")
    @Operation(summary = "Confirm payment", description = "Receives confirmation of a payment")
    @ApiResponse(responseCode = "200", description = "")
    public ResponseEntity<PaymentDto> confirmPayment(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Payment details to be confirmed", required = true)
                                                         @RequestBody PaymentConfirmationRequest paymentConfirmationRequest) {
        Payment payment = this.paymentServicePort.confirmPayment(paymentConfirmationRequest.getExternalReference(), paymentConfirmationRequest.getPaymentStatus());

        PaymentDto paymentDto = PaymentMapperDto.toDto(payment);
        return ResponseEntity.ok(paymentDto);
    }
}
