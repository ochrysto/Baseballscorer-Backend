package com.example.baseballscoresheet.exceptionHandling;

public class PlayerIsPartOfATeamException extends RuntimeException {
    public PlayerIsPartOfATeamException(String message) {
        super(message);
    }
}