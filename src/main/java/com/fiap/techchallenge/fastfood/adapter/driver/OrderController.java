package com.fiap.techchallenge.fastfood.adapter.driver;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.OrderDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.CreateOrderRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.UpdateOrderStatusRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.OrderItemMapperRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.OrderMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.OrderServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Management", description = "Operations related to order management")
public class OrderController {

    @Autowired
    private OrderServicePort orderServicePort;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new order", description = "Create a new order in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public ResponseEntity<OrderDto> generateOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order to be created", required = true) @RequestBody CreateOrderRequest request) {

        Order createdOrder = orderServicePort.generateOrder(
                request.getUserId(),
                OrderItemMapperRequest.mapToDomain(request.getItems())
        );

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrder.getId())
                .toUri();

        return ResponseEntity.created(uri).body(OrderMapperDto.toDto(createdOrder));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve an order by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Order ID provided is invalid"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDto> findById(
            @Parameter(description = "ID of the order to be retrieved", required = true) @PathVariable Long id) {

        Order order = orderServicePort.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(OrderMapperDto.toDto(order));
    }

    @GetMapping()
    @Operation(summary = "Retrieve orders based on filter criteria", description = "Retrieve a list of orders by status and/or user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of orders retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided"),
            @ApiResponse(responseCode = "404", description = "No orders found based on the provided criteria")
    })
    public ResponseEntity<List<OrderDto>> findOrders(
            @Parameter(description = "Status of orders to be retrieved") @RequestParam(required = false) String status,
            @Parameter(description = "User ID of orders to be retrieved") @RequestParam(required = false) Long userId) {

        List<Order> orders;
        if (status != null && userId != null) {
            // TODO: implementar filtros
            // orders = orderServicePort.findByOrderStatusAndUserId(status, userId);
            orders = null;
        } else if (status != null) {
            orders = orderServicePort.findByStatus(status);
        } else if (userId != null) {
            orders = orderServicePort.findByUserId(userId);
        } else {
            orders = orderServicePort.findAll();
        }

        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<OrderDto> ordersDtos = orders.stream()
                .map(OrderMapperDto::toDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(ordersDtos);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update an order status", description = "Update the status of an order in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDto> updateStatus(
            @Parameter(description = "ID of the order to update", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New order status to be updated", required = true) @RequestBody UpdateOrderStatusRequest request) {

        orderServicePort.updateOrderStatus(id, request.getNewStatus());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
