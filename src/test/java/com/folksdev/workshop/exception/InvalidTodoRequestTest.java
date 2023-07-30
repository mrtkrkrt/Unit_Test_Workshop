package com.folksdev.workshop.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidTodoRequestTest {
    @Test
    public void testEmptyConstructor() {
        InvalidTodoRequest exception = new InvalidTodoRequest();
        assertEquals(null, exception.getMessage());
    }

    @Test
    public void testMessageConstructor() {
        String errorMessage = "Invalid Todo Request";
        InvalidTodoRequest exception = new InvalidTodoRequest(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testCustomErrorMessage() {
        String errorMessage = "This is a custom error message.";
        InvalidTodoRequest exception = new InvalidTodoRequest(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}