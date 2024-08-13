package com.fiap.techchallenge.fastfood.adapter.driver;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.CategoryDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.CreateCategoryRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.UpdateCategoryRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.CategoryMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Category;
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

@RestController()
@RequestMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Category Management", description = "Operations related to categories management")
public class CategoryController {

        @Autowired
        private CategoryServicePort categoryServicePort;

        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = "Create a new category", description = "Register a new category in the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Category created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(examples = @ExampleObject(value = """
                                {
                                  "timestamp": "2024-08-10T18:10:23.3837517",
                                  "message": "Category already exists: string",
                                  "details": "uri=/categories"
                                }""")))
        })
        public ResponseEntity<CategoryDto> register(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category details to be created", required = true) @RequestBody CreateCategoryRequest request) {

                Category createdCategory = categoryServicePort.insertCategory(CategoryMapperDto.toDomain(request));

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
                        @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(examples = @ExampleObject(value = """
                                {
                                  "timestamp": "2024-08-10T18:23:47.1067008",
                                  "message": "Category not found for id: 500",
                                  "details": "uri=/categories/500"
                                }""")))
        })
        public ResponseEntity<CategoryDto> findById(
                        @Parameter(description = "ID of the category to retrieve", required = true) @PathVariable Long id) {
                CategoryDto categoryDto = CategoryMapperDto.toDto(categoryServicePort.getCategoryById(id));
                return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
        }

        @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = "Update a category", description = "Update the details of a category in the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Category updated successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(examples = @ExampleObject(value = """
                                {
                                  "timestamp": "2024-08-10T18:10:23.3837517",
                                  "message": "Category already exists: string",
                                  "details": "uri=/categories"
                                }"""))),
                        @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(examples = @ExampleObject(value = """
                                {
                                  "timestamp": "2024-08-10T18:23:47.1067008",
                                  "message": "Category not found for id: 500",
                                  "details": "uri=/categories/500"
                                }""")))
        })
        public ResponseEntity<CategoryDto> update(
                        @Parameter(description = "ID of the category to update", required = true) @PathVariable Long id,
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category details to be updated", required = true) @RequestBody UpdateCategoryRequest request) {
                Category updatedCategory = categoryServicePort.updateCategory(id, CategoryMapperDto.toDomain(request));
                CategoryDto categoryDto = CategoryMapperDto.toDto(updatedCategory);
                return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
        }
}
