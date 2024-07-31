package com.fiap.techchallenge.fastfood.adapter.driven.infra.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fiap.techchallenge.fastfood.core.exceptions.DefaultExceptionBody;
import com.fiap.techchallenge.fastfood.core.exceptions.InvalidOrderQuantityException;

@ControllerAdvice
public class OrderItemExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DefaultExceptionBody> handleIllegalArgumentException(IllegalArgumentException ex,
                    WebRequest request) {
            DefaultExceptionBody exception = new DefaultExceptionBody(
                            LocalDateTime.now(),
                            ex.getMessage(),
                            request.getDescription(false));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

    @ExceptionHandler(InvalidOrderQuantityException.class)
    public ResponseEntity<DefaultExceptionBody> handleInvalidOrderQuantityException(InvalidOrderQuantityException ex,
                    WebRequest request) {
            DefaultExceptionBody exception = new DefaultExceptionBody(
                            LocalDateTime.now(),
                            ex.getMessage(),
                            request.getDescription(false));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

}
