package com.fiap.techchallenge.fastfood.adapter.driven.infra.handlers;

import com.fiap.techchallenge.fastfood.core.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ProductExceptionHandler {

        @ExceptionHandler(ProductNotFoundException.class)
        public ResponseEntity<DefaultExceptionBody> handleProductNotFoundException(ProductNotFoundException ex,
                        WebRequest request) {
                DefaultExceptionBody exception = new DefaultExceptionBody(
                                LocalDateTime.now(),
                                ex.getMessage(),
                                request.getDescription(false));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                        WebRequest request) {
                List<String> errors = ex.getBindingResult().getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();

                return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        private Map<String, List<String>> getErrorsMap(List<String> errors) {
                Map<String, List<String>> errorResponse = new HashMap<>();
                errorResponse.put("errors", errors);
                return errorResponse;
        }

        @ExceptionHandler(InvalidPriceException.class)
        public ResponseEntity<DefaultExceptionBody> handleInvalidPriceException(InvalidPriceException ex,
                                                                                WebRequest request) {
                DefaultExceptionBody exception = new DefaultExceptionBody(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<DefaultExceptionBody> handleRuntimeException(RuntimeException ex,
                                                                                WebRequest request) {
                DefaultExceptionBody exception = new DefaultExceptionBody(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
                log.error("Unexpected error", ex);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
        }

}
