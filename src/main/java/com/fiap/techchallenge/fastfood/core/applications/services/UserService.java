package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.domain.User;
import com.fiap.techchallenge.fastfood.core.applications.ports.UserRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.UserServicePort;
import com.fiap.techchallenge.fastfood.core.validators.UserValidator;

import java.time.LocalDateTime;
import java.util.List;

public class UserService implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;
    private final UserValidator userValidator;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.userValidator = new UserValidator(userRepositoryPort);
    }

    @Override
    public User register(User user) {
        userValidator.validateEmailNotExists(user.getEmail());
        userValidator.validateCpfNotExists(user.getCpf()); 
        return this.userRepositoryPort
                .register(new User(user.getName(), user.getEmail(), user.getCpf(), LocalDateTime.now()));
    }

    @Override
    public List<User> findAll() {
        return this.userRepositoryPort.findAll();
    }

    @Override
    public User findById(Long userId) {
        userValidator.validateUserExistsById(userId);
        return this.userRepositoryPort.findById(userId);
    }

    @Override
    public User findByEmail(String email) {
        userValidator.validateUserExistsByEmail(email);
        return this.userRepositoryPort.findByEmail(email);
    }

    @Override
    public User findByCpf(String cpf) {
        userValidator.validateUserExistsByCpf(cpf);
        return this.userRepositoryPort.findByCpf(cpf);
    }
}
