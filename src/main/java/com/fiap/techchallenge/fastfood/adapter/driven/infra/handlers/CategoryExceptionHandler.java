package com.fiap.techchallenge.fastfood.adapter.driven.infra.handlers;

import com.fiap.techchallenge.fastfood.core.exceptions.CategoryAlreadyExistsException;
import com.fiap.techchallenge.fastfood.core.exceptions.CategoryNotFoundException;
import com.fiap.techchallenge.fastfood.core.exceptions.DefaultExceptionBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class CategoryExceptionHandler {

        @ExceptionHandler(CategoryNotFoundException.class)
        public ResponseEntity<DefaultExceptionBody> handleCategoryNotFoundException(CategoryNotFoundException ex,
                        WebRequest request) {
                DefaultExceptionBody exception = new DefaultExceptionBody(
                                LocalDateTime.now(),
                                ex.getMessage(),
                                request.getDescription(false));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
        }

        @ExceptionHandler(CategoryAlreadyExistsException.class)
        public ResponseEntity<DefaultExceptionBody> handleCategoryAlreadyExistsException(
                        CategoryAlreadyExistsException ex,
                        WebRequest request) {
                DefaultExceptionBody exception = new DefaultExceptionBody(
                                LocalDateTime.now(),
                                ex.getMessage(),
                                request.getDescription(false));
                return ResponseEntity.status(HttpStatus.CONFLICT).body(exception);
        }
}
