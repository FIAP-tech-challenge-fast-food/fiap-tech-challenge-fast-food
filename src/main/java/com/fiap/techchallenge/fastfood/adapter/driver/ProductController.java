package com.fiap.techchallenge.fastfood.adapter.driver;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.ProductDto;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.ProductMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("products")
@Tag(name = "Product Management", description = "Operations related to product management")
public class ProductController {

    @Autowired
    private ProductServicePort productServicePort;

    @GetMapping("/")
    @Operation(summary = "Get products by category ID", description = "Retrieve a list of products by their category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category ID provided"),
            @ApiResponse(responseCode = "404", description = "No products found for the given category ID")
    })
    public ResponseEntity<List<ProductDto>> findByCategoryId(
            @Parameter(description = "ID of the category to retrieve products for", required = true) @RequestParam Long categoryId) {
        List<Product> products = productServicePort.findByCategoryId(categoryId);
        List<ProductDto> productDtos = products.stream().map(ProductMapperDto::toDto).toList();

        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
    }
}
