package com.folksdev.workshop.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {
    @Test
    public void testEmptyConstructor() {
        UserNotFoundException exception = new UserNotFoundException();
        assertEquals(null, exception.getMessage());
    }

    @Test
    public void testMessageConstructor() {
        String errorMessage = "User Not Found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testCustomErrorMessage() {
        String errorMessage = "Custom error message for User Not Found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}