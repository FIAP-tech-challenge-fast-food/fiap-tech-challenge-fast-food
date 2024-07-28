package com.fiap.techchallenge.fastfood.adapter.driver;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.OrderDto;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.OrderMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.OrderServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import com.fiap.techchallenge.fastfood.core.domain.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orders")
@Tag(name = "Order Management", description = "Operations related to order management")
public class OrderController {

    @Autowired
    OrderServicePort orderServicePort;

    @GetMapping("/{status}")
    @Operation(summary = "Get all orders by status", description = "Retrieve a list of all orders by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of orders retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No order was found")
    })
    public ResponseEntity<List<OrderDto>> findByStatus(
            @Parameter(description = "Status of orders to be retrieved", required = true) @PathVariable OrderStatus orderStatus) {
        List<Order> orders = orderServicePort.findByStatus(orderStatus);

        List<OrderDto> ordersDtos = orders.stream()
                .map(OrderMapperDto::toDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(ordersDtos);
    }
}
