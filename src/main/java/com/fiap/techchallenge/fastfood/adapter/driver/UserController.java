package com.fiap.techchallenge.fastfood.adapter.driver;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.UserDto;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.UserMapperDto;
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

        @PostMapping
        @Operation(summary = "Create a new user", description = "Register a new user in the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "User created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input provided")
        })
        public ResponseEntity<UserDto> register(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details to be created", required = true) @RequestBody UserDto user) {

                User createdUser = userServicePort.register(UserMapperDto.toDomain(user));

                URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(createdUser.getId()).toUri();

                return ResponseEntity.created(uri).body(UserMapperDto.toDto(createdUser));
        }

        @GetMapping
        @Operation(summary = "Get all users", description = "Retrieve a list of all users")
        @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
        public ResponseEntity<List<UserDto>> findAll() {
                List<User> users = userServicePort.findAll();
                List<UserDto> usersDtos = users.stream().map(UserMapperDto::toDto).collect(Collectors.toList());

                return ResponseEntity.status(HttpStatus.OK).body(usersDtos);
        }

        @GetMapping("/{id}")
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

        @GetMapping("/by-email")
        @Operation(summary = "Get user by email", description = "Retrieve a user by their email address")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "User not found")
        })
        public ResponseEntity<UserDto> findByEmail(
                        @Parameter(description = "Email address of the user to be retrieved", required = true) @RequestParam String email) {

                User user = userServicePort.findByEmail(email);
                return ResponseEntity.status(HttpStatus.OK).body(UserMapperDto.toDto(user));
        }

        @GetMapping("/by-cpf")
        @Operation(summary = "Get user by cpf", description = "Retrieve a user by their cpf")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "User not found")
        })
        public ResponseEntity<UserDto> findByCpf(
                        @Parameter(description = "Cpf of the user to be retrieved", required = true) @RequestParam String cpf) {

                User user = userServicePort.findByCpf(cpf);
                return ResponseEntity.status(HttpStatus.OK).body(UserMapperDto.toDto(user));
        }
}
