package com.folksdev.workshop.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {
    @Test
    void testEmptyConstructor() {
        UserNotFoundException exception = new UserNotFoundException();
        assertEquals(null, exception.getMessage());
    }

    @Test
    void testMessageConstructor() {
        String errorMessage = "User Not Found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testCustomErrorMessage() {
        String errorMessage = "Custom error message for User Not Found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}