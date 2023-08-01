package com.folksdev.workshop.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidTodoRequestTest {
    @Test
    void testEmptyConstructor() {
        InvalidTodoRequest exception = new InvalidTodoRequest();
        assertEquals(null, exception.getMessage());
    }

    @Test
    void testMessageConstructor() {
        String errorMessage = "Invalid Todo Request";
        InvalidTodoRequest exception = new InvalidTodoRequest(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testCustomErrorMessage() {
        String errorMessage = "This is a custom error message.";
        InvalidTodoRequest exception = new InvalidTodoRequest(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}