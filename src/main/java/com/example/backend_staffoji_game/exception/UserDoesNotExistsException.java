package com.example.backend_staffoji_game.exception;

import org.springframework.http.HttpStatus;

public class UserDoesNotExistsException extends RuntimeException{
    public UserDoesNotExistsException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

}
