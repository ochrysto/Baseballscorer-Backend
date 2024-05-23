package com.example.baseballscoresheet.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class PlayerIsNotAvailableException extends RuntimeException {
    public PlayerIsNotAvailableException(String message) {
        super(message);
    }
}
