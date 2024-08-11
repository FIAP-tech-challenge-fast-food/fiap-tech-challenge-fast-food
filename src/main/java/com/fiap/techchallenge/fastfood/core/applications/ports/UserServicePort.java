package com.fiap.techchallenge.fastfood.core.applications.ports;

import java.util.List;
import com.fiap.techchallenge.fastfood.core.domain.User;

public interface UserServicePort {

    public User register(User user);

    public List<User> findAll();

    public User findById(Long userId);

    public User findByEmail(String email);

    public User findByCpf(String cpf);

    public User findByIdentifier(String type, String value);
}
