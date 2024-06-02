package com.example.baseballscoresheet.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class DoubleInputException extends RuntimeException {
    public DoubleInputException(String message) {
        super(message);
    }
}
