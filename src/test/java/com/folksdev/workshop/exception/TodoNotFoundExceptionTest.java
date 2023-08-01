package com.folksdev.workshop.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoNotFoundExceptionTest {
    @Test
    void testEmptyConstructor() {
        TodoNotFoundException exception = new TodoNotFoundException();
        assertEquals(null, exception.getMessage());
    }

    @Test
    void testMessageConstructor() {
        String errorMessage = "Todo Not Found";
        TodoNotFoundException exception = new TodoNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testCustomErrorMessage() {
        String errorMessage = "Custom error message for Todo Not Found";
        TodoNotFoundException exception = new TodoNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}