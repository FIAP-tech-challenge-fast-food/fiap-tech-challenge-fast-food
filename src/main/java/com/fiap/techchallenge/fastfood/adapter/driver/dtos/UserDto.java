package com.fiap.techchallenge.fastfood.adapter.driver.dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private LocalDateTime createdAt;

    public UserDto(String name, String email, String cpf, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.createdAt = createdAt;
    }
}
