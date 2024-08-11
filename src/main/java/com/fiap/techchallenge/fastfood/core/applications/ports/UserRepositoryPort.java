package com.fiap.techchallenge.fastfood.core.applications.ports;

import java.util.List;

import com.fiap.techchallenge.fastfood.core.domain.User;

public interface UserRepositoryPort {

    User register(User user);

    List<User> findAll();

    User findById(Long id);

    User findByEmail(String email);

    User findByCpf(String cpf);

    User findByIdentifier(String type, String value);
}
