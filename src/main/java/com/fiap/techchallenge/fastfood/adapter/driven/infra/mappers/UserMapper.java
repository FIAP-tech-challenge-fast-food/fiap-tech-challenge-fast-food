package com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers;

import com.fiap.techchallenge.fastfood.core.domain.User;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return new User(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getCpf(),
                userEntity.getCreatedAt());
    }

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getCreatedAt());
    }

}
