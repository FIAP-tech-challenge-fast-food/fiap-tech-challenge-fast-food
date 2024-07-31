package com.fiap.techchallenge.fastfood.core.exceptions;

public class EmptyRequestBodyException extends RuntimeException {
    public EmptyRequestBodyException(String requestBodyCannotBeEmpty) {
        super(requestBodyCannotBeEmpty);
    }
}
