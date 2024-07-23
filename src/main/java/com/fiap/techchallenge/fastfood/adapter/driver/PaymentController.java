package com.fiap.techchallenge.fastfood.adapter.driver;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.PaymentDto;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.PaymentMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Payment;

import java.net.URI;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentServicePort paymentServicePort;

    @PostMapping
    public ResponseEntity<PaymentDto> register(@RequestBody PaymentDto paymentDto) {
        Payment payment = paymentServicePort.registerPayment(PaymentMapperDto.toDomain(paymentDto));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(payment.getId()).toUri();

        return ResponseEntity.created(uri).body(PaymentMapperDto.toDto(payment)); 
    }

    @GetMapping
    public ResponseEntity<List<PaymentDto>> findAll() {
        List<Payment> payments = paymentServicePort.findAll();
        List<PaymentDto> paymentDtos = payments.stream().map(PaymentMapperDto::toDto).collect(Collectors.toList());

        return ResponseEntity.ok(paymentDtos);
    }

    @GetMapping("/order")
    public ResponseEntity<PaymentDto> findByOrderId(@RequestParam Long orderId) {
        if (orderId == null || orderId == 0) {
            throw new IllegalArgumentException("Id is invalid");
        }

        Payment payment = paymentServicePort.findByOrderId(orderId);
       
        return ResponseEntity.ok(PaymentMapperDto.toDto(payment));
    }

}