package com.fiap.techchallenge.fastfood.adapter.driver.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Long id;

    @NotNull(message = "Invalid name: cannot be empty")
    @NotEmpty(message = "Invalid name: cannot be empty")
    private String name;
    
    @NotNull(message = "Invalid email: cannot be empty")
    @NotEmpty(message = "Invalid email: cannot be empty")
    private String email;

    @NotNull(message = "Invalid cpf: cannot be empty")
    @NotEmpty(message = "Invalid cpf: cannot be empty")
    private String cpf;
    
    private LocalDateTime createdAt;
}
