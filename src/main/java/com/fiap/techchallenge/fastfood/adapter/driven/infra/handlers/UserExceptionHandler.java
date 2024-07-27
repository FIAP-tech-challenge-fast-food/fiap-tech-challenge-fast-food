package com.fiap.techchallenge.fastfood.adapter.driven.infra.handlers;

import com.fiap.techchallenge.fastfood.core.exceptions.CpfAlreadyExistsException;
import com.fiap.techchallenge.fastfood.core.exceptions.DefaultExceptionBody;
import com.fiap.techchallenge.fastfood.core.exceptions.EmailAlreadyExistsException;
import com.fiap.techchallenge.fastfood.core.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserExceptionHandler {

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<DefaultExceptionBody> handleUserNotFoundException(UserNotFoundException ex,
                        WebRequest request) {
                DefaultExceptionBody exception = new DefaultExceptionBody(
                                LocalDateTime.now(),
                                ex.getMessage(),
                                request.getDescription(false));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
        }

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<DefaultExceptionBody> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex,
                        WebRequest request) {
                DefaultExceptionBody exception = new DefaultExceptionBody(
                                LocalDateTime.now(),
                                ex.getMessage(),
                                request.getDescription(false));
                return ResponseEntity.status(HttpStatus.CONFLICT).body(exception);
        }

        @ExceptionHandler(CpfAlreadyExistsException.class)
        public ResponseEntity<DefaultExceptionBody> handleCpfAlreadyExistsException(CpfAlreadyExistsException ex,
                        WebRequest request) {
                DefaultExceptionBody exception = new DefaultExceptionBody(
                                LocalDateTime.now(),
                                ex.getMessage(),
                                request.getDescription(false));
                return ResponseEntity.status(HttpStatus.CONFLICT).body(exception);
        }
}
