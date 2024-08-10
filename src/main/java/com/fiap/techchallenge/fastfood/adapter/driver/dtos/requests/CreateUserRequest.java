package com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserRequest {
    private String name;
    private String email;
    private String cpf;
}
