package com.fiap.techchallenge.fastfood.adapter.driven.infra.handlers;

import com.fiap.techchallenge.fastfood.core.exceptions.DefaultExceptionBody;
import com.fiap.techchallenge.fastfood.core.exceptions.InvalidOrderStatusException;
import com.fiap.techchallenge.fastfood.core.exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<DefaultExceptionBody> handleOrderNotFoundException(OrderNotFoundException ex,
                                                                               WebRequest request) {
        DefaultExceptionBody exception = new DefaultExceptionBody(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    public ResponseEntity<DefaultExceptionBody> handleInvalidOrderStatusException(InvalidOrderStatusException ex,
                                                                             WebRequest request) {
        DefaultExceptionBody exception = new DefaultExceptionBody(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }
}
