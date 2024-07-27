package com.fiap.techchallenge.fastfood.core.validators;

import com.fiap.techchallenge.fastfood.core.applications.ports.UserRepositoryPort;
import com.fiap.techchallenge.fastfood.core.exceptions.CpfAlreadyExistsException;
import com.fiap.techchallenge.fastfood.core.exceptions.EmailAlreadyExistsException;
import com.fiap.techchallenge.fastfood.core.exceptions.UserNotFoundException;

public class UserValidator {

    private final UserRepositoryPort userRepositoryPort;

    public UserValidator(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public void validateEmailNotExists(String email) {
        if (userRepositoryPort.findByEmail(email) != null) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    public void validateCpfNotExists(String cpf) {
        if (userRepositoryPort.findByCpf(cpf) != null) {
            throw new CpfAlreadyExistsException(cpf);
        }
    }

    public void validateUserExistsById(Long userId) {
        if (userRepositoryPort.findById(userId) == null) {
            throw new UserNotFoundException(userId);
        }
    }

    public void validateUserExistsByEmail(String email) {
        if (userRepositoryPort.findByEmail(email) == null) {
            throw new UserNotFoundException(email);
        }
    }

    public void validateUserExistsByCpf(String cpf) {
        if (userRepositoryPort.findByCpf(cpf) == null) {
            throw new UserNotFoundException(cpf);
        }
    }
}
