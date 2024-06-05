package com.example.baseballscoresheet.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedError extends RuntimeException {
    public NotImplementedError(String message) {
        super(message);
    }
}