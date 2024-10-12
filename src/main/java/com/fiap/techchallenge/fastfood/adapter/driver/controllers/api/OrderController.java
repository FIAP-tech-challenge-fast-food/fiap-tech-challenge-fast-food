package com.fiap.techchallenge.fastfood.adapter.driver.controllers.api;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.OrderDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.CreateOrderRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.UpdateOrderReferenceRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.UpdateOrderStatusRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.OrderItemMapperRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.OrderMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.OrderServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
import java.util.List;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Order Management", description = "Operations related to order management")
public class OrderController {

    @Autowired
    private OrderServicePort orderServicePort;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new order", description = "Create a new order in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(examples = @ExampleObject(value = """
                    {
                      "timestamp": "2024-08-10T18:37:17.7914283",
                      "message": "Order status provided is invalid: status",
                      "details": "uri=/orders/1/status"
                    }""")))
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

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get order by ID", description = "Retrieve an order by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(examples = @ExampleObject(value = """
                    {
                      "timestamp": "2024-08-10T18:38:09.8767397",
                      "message": "Order not found with id: 500",
                      "details": "uri=/orders/500/status"
                    }""")))
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
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(examples = @ExampleObject(value = """
                    {
                      "timestamp": "2024-08-10T18:38:09.8767397",
                      "message": "Order not found with id: 500",
                      "details": "uri=/orders/500/status"
                    }""")))
    })
    public ResponseEntity<List<OrderDto>> findOrders(
            @Parameter(description = "Status of orders to be retrieved") @RequestParam(required = false) String status,
            @Parameter(description = "User ID of orders to be retrieved") @RequestParam(required = false) Long userId) {

        List<Order> orders = orderServicePort.findOrdersByQueryParams(status, userId);

        List<OrderDto> ordersDtos = orders.stream()
                .map(OrderMapperDto::toDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(ordersDtos);
    }

    @PutMapping(path = "/{id}/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an order status", description = "Update the status of an order in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(examples = @ExampleObject(value = """
                    {
                      "timestamp": "2024-08-10T18:37:17.7914283",
                      "message": "Order status provided is invalid: status",
                      "details": "uri=/orders/1/status"
                    }""")))
    })
    public ResponseEntity<OrderDto> updateStatus(
            @Parameter(description = "ID of the order to update", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New order status to be updated", required = true) @RequestBody UpdateOrderStatusRequest request) {

        orderServicePort.updateOrderStatus(id, request.getNewStatus());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/{id}/reference", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an order reference", description = "Update the reference of an order in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order reference updated successfully")
    })
    public ResponseEntity<OrderDto> updateReference(
            @Parameter(description = "ID of the order to update", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New order reference to be updated", required = true) @RequestBody UpdateOrderReferenceRequest request) {

        orderServicePort.updateOrderReference(id, request.getReference());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
