package com.fiap.techchallenge.fastfood.adapter.driver;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.ProductDto;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.ProductMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Product Management", description = "Operations related to product management")
public class ProductController {

    @Autowired
    private ProductServicePort productServicePort;

    @GetMapping("/")
    @Operation(summary = "Get products by category ID", description = "Retrieve a list of products by their category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category ID provided")
    })
    public ResponseEntity<List<ProductDto>> findByCategoryId(
            @Parameter(description = "ID of the category to retrieve products for", required = true) @RequestParam @Valid @NotNull Long categoryId) {
        List<Product> products = productServicePort.findByCategoryId(categoryId);
        List<ProductDto> productDtos = products.stream().map(ProductMapperDto::toDto).toList();

        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a product by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> findById(
            @Parameter(description = "ID of the user to be retrieved", required = true) @PathVariable @Valid @NotNull Long id
    ) {
        Product product = productServicePort.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(ProductMapperDto.toDto(product));
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new product", description = "Register a new product in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public ResponseEntity<ProductDto> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product details to be created", required = true)
            @RequestBody @Valid @NotNull ProductDto productDto) {

        Product createdProduct = productServicePort.register(ProductMapperDto.toDomain(productDto));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdProduct.getId()).toUri();

        return ResponseEntity.created(uri).body(ProductMapperDto.toDto(createdProduct));
    }
}
