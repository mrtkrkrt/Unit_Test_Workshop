package com.folksdev.workshop.exception;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
