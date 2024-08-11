package com.fiap.techchallenge.fastfood.adapter.driver;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            @ApiResponse(responseCode = "404", description = "Invalid order provided", content = @Content(examples = @ExampleObject(value = """
                    {
                      "timestamp": "2024-08-10T18:49:21.3340294",
                      "message": "Order not found with id: 0",
                      "details": "uri=/payments"
                    }""")))
    })
    public ResponseEntity<PaymentDto> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Payment details to be created", required = true) @RequestBody CreatePaymentRequest createPaymentRequest) {
        Payment payment = paymentServicePort.registerPayment(PaymentMapperDto.toDomain(createPaymentRequest));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(payment.getId()).toUri();

        return ResponseEntity.created(uri).body(PaymentMapperDto.toDto(payment));
    }

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
}
