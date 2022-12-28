package com.example.recipesapi.v1.security.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(final String message) {
        super(message);
    }

   public UserAlreadyExistsException() {}
}
