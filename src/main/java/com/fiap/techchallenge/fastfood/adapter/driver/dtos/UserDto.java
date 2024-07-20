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
}
