package com.fiap.techchallenge.fastfood.adapter.driven.infra;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.UserEntity;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.mappers.UserMapper;
import com.fiap.techchallenge.fastfood.core.applications.ports.UserRepositoryPort;
import com.fiap.techchallenge.fastfood.core.domain.User;
import com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories.UserRepository;

@Component
public class UserJpaPort implements UserRepositoryPort {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userEntity = userRepository.save(userEntity);
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDomain)
                .orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .map(UserMapper::toDomain)
                .orElse(null);
    }

    @Override
    public User findByCpf(String cpf) {
        return userRepository.findAll().stream()
                .filter(user -> user.getCpf().equals(cpf))
                .findFirst()
                .map(UserMapper::toDomain)
                .orElse(null);
    }
}
