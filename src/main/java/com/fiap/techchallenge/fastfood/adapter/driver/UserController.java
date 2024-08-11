package com.fiap.techchallenge.fastfood.adapter.driver;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.UserDto;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.CreateUserRequest;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.UserMapperDto;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.UserMapperRequest;
import com.fiap.techchallenge.fastfood.core.applications.ports.UserServicePort;
import com.fiap.techchallenge.fastfood.core.domain.User;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Operations related to user management")
public class UserController {

        @Autowired
        private UserServicePort userServicePort;

        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = "Create a new user", description = "Register a new user in the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "User created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input provided")
        })
        public ResponseEntity<UserDto> register(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details to be created", required = true) @RequestBody CreateUserRequest request) {

                User createdUser = userServicePort.register(UserMapperRequest.toDomain(request));
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(createdUser.getId()).toUri();

                return ResponseEntity.created(uri).body(UserMapperDto.toDto(createdUser));
        }

        @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = "Get all users", description = "Retrieve a list of all users")
        @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
        public ResponseEntity<List<UserDto>> findAll() {
                List<User> users = userServicePort.findAll();
                List<UserDto> usersDtos = users.stream().map(UserMapperDto::toDto).collect(Collectors.toList());

                return ResponseEntity.status(HttpStatus.OK).body(usersDtos);
        }

        @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = "Get user by ID", description = "Retrieve a user by their unique ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "User not found")
        })
        public ResponseEntity<UserDto> findById(
                        @Parameter(description = "ID of the user to be retrieved", required = true) @PathVariable Long id) {

                User user = userServicePort.findById(id);
                return ResponseEntity.status(HttpStatus.OK).body(UserMapperDto.toDto(user));
        }

        @GetMapping(path = "/{type}/{value}", produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = "Get user by type", description = "Retrieve a user by their email or cpf")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "User not found")
        })
        public ResponseEntity<UserDto> findUserByIdentifier(
                        @Parameter(description = "Type of the search, either 'email' or 'cpf'", required = true) @PathVariable String type,
                        @Parameter(description = "Value of the type to be searched", required = true) @PathVariable String value) {

                User user = userServicePort.findByIdentifier(type, value);
                return ResponseEntity.status(HttpStatus.OK).body(UserMapperDto.toDto(user));
        }
}
