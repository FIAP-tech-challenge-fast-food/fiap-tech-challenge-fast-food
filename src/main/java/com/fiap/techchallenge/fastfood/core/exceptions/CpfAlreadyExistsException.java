package com.fiap.techchallenge.fastfood.core.exceptions;

public class CpfAlreadyExistsException extends RuntimeException {
    public CpfAlreadyExistsException(String cpf) {
        super("Cpf not available: " + cpf);
    }
}
