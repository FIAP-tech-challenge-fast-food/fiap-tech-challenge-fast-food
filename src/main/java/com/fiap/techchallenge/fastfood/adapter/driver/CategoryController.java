package com.fiap.techchallenge.fastfood.adapter.driver;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.CategoryDto;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.CategoryMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Category;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/category")
@Tag(name = "Category Management", description = "Operations related to categories management")
public class CategoryController {

        @Autowired
        private CategoryServicePort categoryServicePort;

        @PostMapping
        @Operation(summary = "Create a new category", description = "Register a new category in the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Category created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input provided")
        })
        public ResponseEntity<CategoryDto> register(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category details to be created", required = true) @RequestBody CategoryDto category) {

                Category createdCategory = categoryServicePort.insertCategory(CategoryMapperDto.toDomain(category));

                URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(createdCategory.getId()).toUri();

                return ResponseEntity.created(uri).body(CategoryMapperDto.toDto(createdCategory));
        }

        @GetMapping
        @Operation(summary = "Get all categories", description = "Retrieve a list of all categories in the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
        })
        public ResponseEntity<List<CategoryDto>> findAll() {
                List<Category> categories = categoryServicePort.getAllCategories();
                List<CategoryDto> categoryDtos = categories.stream().map(CategoryMapperDto::toDto).toList();
                return ResponseEntity.status(HttpStatus.OK).body(categoryDtos);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get a category by ID", description = "Retrieve a category by its ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Category retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "Category not found")
        })
        public ResponseEntity<CategoryDto> findById(
                        @Parameter(description = "ID of the category to retrieve", required = true) @PathVariable Long id) {
                CategoryDto categoryDto = CategoryMapperDto.toDto(categoryServicePort.getCategoryById(id));
                return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
        }

        @PutMapping("/{id}")
        @Operation(summary = "Update a category", description = "Update the details of a category in the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Category updated successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input provided"),
                        @ApiResponse(responseCode = "404", description = "Category not found")
        })
        public ResponseEntity<CategoryDto> update(
                        @Parameter(description = "ID of the category to update", required = true) @PathVariable Long id,
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category details to be updated", required = true) @RequestBody CategoryDto category) {
                Category updatedCategory = categoryServicePort.updateCategory(id, CategoryMapperDto.toDomain(category));
                CategoryDto categoryDto = CategoryMapperDto.toDto(updatedCategory);
                return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
        }
}
