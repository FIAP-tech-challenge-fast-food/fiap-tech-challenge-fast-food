package com.fiap.techchallenge.fastfood.adapter.driver;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.UserDto;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.UserMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.UserServicePort;
import com.fiap.techchallenge.fastfood.core.domain.User;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServicePort userServicePort;

    @PostMapping
    public ResponseEntity<UserDto> register(@RequestBody UserDto user) {
        User createdUser = userServicePort.register(UserMapperDto.toDomain(user));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();

        return ResponseEntity.created(uri).body(UserMapperDto.toDto(createdUser));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<User> users = userServicePort.findAll();
        List<UserDto> usersDtos = users.stream().map(UserMapperDto::toDto).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(usersDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        User user = userServicePort.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapperDto.toDto(user));
    }

    @GetMapping("/email")
    public ResponseEntity<UserDto> findByEmail(@RequestParam String email) {
        User user = userServicePort.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapperDto.toDto(user));
    }
}
