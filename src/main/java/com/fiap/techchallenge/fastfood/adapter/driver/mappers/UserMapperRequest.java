package com.fiap.techchallenge.fastfood.adapter.driver.mappers;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.requests.CreateUserRequest;
import com.fiap.techchallenge.fastfood.core.domain.User;

import java.time.LocalDateTime;

public class UserMapperRequest {

    public static User toDomain(CreateUserRequest createUserRequest) {
        if (createUserRequest == null) {
            return null;
        }

        return new User(
                createUserRequest.getName(),
                createUserRequest.getEmail(),
                createUserRequest.getCpf(),
                LocalDateTime.now());
    }
}
