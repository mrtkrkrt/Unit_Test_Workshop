package com.folksdev.workshop.exception;

public class InvalidTodoRequest extends RuntimeException {
    public InvalidTodoRequest() {
        super();
    }

    public InvalidTodoRequest(String message) {
        super(message);
    }
}
