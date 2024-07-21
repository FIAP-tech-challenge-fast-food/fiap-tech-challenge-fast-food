package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.UserRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.UserServicePort;
import com.fiap.techchallenge.fastfood.core.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserService implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User register(User user) {
        return this.userRepositoryPort
                .register(new User(user.getName(), user.getEmail(), user.getCpf(), LocalDateTime.now()));
    }

    @Override
    public List<User> findAll() {
        return this.userRepositoryPort.findAll();
    }

    @Override
    public User findById(Long userId) {
        return this.userRepositoryPort.findById(userId);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepositoryPort.findByEmail(email);
    }
}
