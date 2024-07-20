package com.fiap.techchallenge.fastfood.core.applications.services;

import com.fiap.techchallenge.fastfood.core.applications.ports.UserRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.UserServicePort;
import com.fiap.techchallenge.fastfood.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserService implements UserServicePort {

    @Autowired
    private UserRepositoryPort userRepositoryPort;

    public User register(User user) {
        return this.userRepositoryPort
                .register(new User(user.getName(), user.getEmail(), user.getCpf(), LocalDateTime.now()));
    }

    public List<User> findAll() {
        return this.userRepositoryPort.findAll();
    }

    public User findById(Long userId) {
        return this.userRepositoryPort.findById(userId);
    }

    public User findByEmail(String email) {
        return this.userRepositoryPort.findByEmail(email);
    }
}
