package com.fiap.techchallenge.fastfood.core.exceptions;

import java.time.LocalDateTime;

public class DefaultExceptionBody {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public DefaultExceptionBody(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
