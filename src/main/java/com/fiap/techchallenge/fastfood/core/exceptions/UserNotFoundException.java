package com.fiap.techchallenge.fastfood.core.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }

    public UserNotFoundException(String param) {
        super("User not found");
    }
}
