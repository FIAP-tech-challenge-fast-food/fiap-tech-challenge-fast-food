package com.fiap.techchallenge.fastfood.adapter.driver;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.ProductDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.ProductRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.ProductMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Product;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

    @GetMapping()
    @Operation(summary = "Get products by category ID", description = "Retrieve a list of products by their category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<List<ProductDto>> findProducts(
            @Parameter(description = "ID of the category to retrieve products for") @RequestParam(required = false) Long categoryId) {
        List<Product> products;
        if(categoryId != null) {
            products = productServicePort.findByCategoryId(categoryId);
        } else {
            products = productServicePort.findAll();
        }

        List<ProductDto> productDtos = products.stream().map(ProductMapperDto::toDto).toList();

        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get product by ID", description = "Retrieve a product by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(examples = @ExampleObject(value = """
                    {
                      "timestamp": "2024-08-10T18:25:17.7020587",
                      "message": "Product not found with id: 1000",
                      "details": "uri=/products/1000"
                    }""")))
    })
    public ResponseEntity<ProductDto> findById(
            @Parameter(description = "ID of the user to be retrieved", required = true) @PathVariable @Valid @NotNull Long id
    ) {
        Product product = productServicePort.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(ProductMapperDto.toDto(product));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new product", description = "Register a new product in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(examples = @ExampleObject(value = """
                    {
                      "errors": [
                        "Invalid price: must be greater or equals 0",
                        "Invalid categoryId: cannot be empty"
                      ]
                    }""")))
    })
    public ResponseEntity<ProductDto> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product details to be created", required = true)
            @RequestBody @Valid ProductRequest productRequest) {

        Product createdProduct = productServicePort.register(ProductMapperDto.toDomain(productRequest));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdProduct.getId()).toUri();

        return ResponseEntity.created(uri).body(ProductMapperDto.toDto(createdProduct));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a product", description = "Update a product in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(examples = @ExampleObject(value = """
                    {
                      "errors": [
                        "Invalid price: must be greater or equals 0",
                        "Invalid categoryId: cannot be empty"
                      ]
                    }"""))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(examples = @ExampleObject(value = """
                    {
                      "timestamp": "2024-08-10T18:25:17.7020587",
                      "message": "Product not found with id: 1000",
                      "details": "uri=/products/1000"
                    }""")))
    })
    public ResponseEntity<ProductDto> update(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product details to be updated", required = true) @RequestBody ProductDto productDto,
                                             @Parameter(description = "ID of the product to be updated", required = true) @PathVariable Long id) {
        Product product = this.productServicePort.update(ProductMapperDto.toDomain(productDto), id);

        return ResponseEntity.ok().body(ProductMapperDto.toDto(product));
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete a product", description = "Delete a product from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product deleted successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(examples = @ExampleObject(value = """
                    {
                      "timestamp": "2024-08-10T18:25:17.7020587",
                      "message": "Product not found with id: 1000",
                      "details": "uri=/products/1000"
                    }""")))
    })
    public ResponseEntity<String> delete(@Parameter(description = "ID of the product to be deleted", required = true) @PathVariable Long id) {
        this.productServicePort.remove(id);
        return ResponseEntity.ok().build();
    }
}
