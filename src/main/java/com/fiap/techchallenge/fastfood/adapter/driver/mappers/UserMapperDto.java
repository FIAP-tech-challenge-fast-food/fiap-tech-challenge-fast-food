package com.fiap.techchallenge.fastfood.adapter.driver.mappers;

import com.fiap.techchallenge.fastfood.core.domain.User;
import com.fiap.techchallenge.fastfood.adapter.driver.dtos.UserDto;

public class UserMapperDto {

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getId(), user.getName(), user.getEmail(), user.getCpf(), user.getCreatedAt());
    }

    public static User toDomain(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return new User(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getCpf(),
                userDto.getCreatedAt());
    }
}
