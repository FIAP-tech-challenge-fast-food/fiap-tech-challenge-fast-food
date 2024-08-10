package com.fiap.techchallenge.fastfood.adapter.driver;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.PaymentDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.CreatePaymentRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.PaymentMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.MediaType;

import java.net.URI;

import io.swagger.v3.oas.annotations.*;

@RestController
@RequestMapping(value = "payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payment Management", description = "Operations related to payment management")
public class PaymentController {

    @Autowired
    private PaymentServicePort paymentServicePort;

    @PostMapping
    @Operation(summary = "Register a new payment", description = "Register a new payment in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
    })
    public ResponseEntity<PaymentDto> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Payment details to be created", required = true) @RequestBody CreatePaymentRequest createPaymentRequest) {
        Payment payment = paymentServicePort.registerPayment(PaymentMapperDto.toDomain(createPaymentRequest));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(payment.getId()).toUri();

        return ResponseEntity.created(uri).body(PaymentMapperDto.toDto(payment));
    }

    @GetMapping
    @Operation(summary = "Get all payments", description = "Retrieve a list of all payments")
    @ApiResponse(responseCode = "200", description = "List of payments retrieved successfully")
    public ResponseEntity<List<PaymentDto>> findAll() {
        List<Payment> payments = paymentServicePort.findAll();
        List<PaymentDto> paymentDtos = payments.stream().map(PaymentMapperDto::toDto).collect(Collectors.toList());

        return ResponseEntity.ok(paymentDtos);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get payment by order ID", description = "Retrieve a payment by its order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<PaymentDto> findByOrderId(
            @Parameter(description = "Order ID of the payment to be retrieved", required = true) @PathVariable @Valid @NotNull Long id) {
        Payment payment = paymentServicePort.findByOrderId(id);

        return ResponseEntity.ok(PaymentMapperDto.toDto(payment));
    }

}