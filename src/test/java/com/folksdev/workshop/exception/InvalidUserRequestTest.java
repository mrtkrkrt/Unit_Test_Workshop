package com.folksdev.workshop.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidUserRequestTest {
    @Test
    public void testEmptyConstructor() {
        InvalidUserRequest exception = new InvalidUserRequest();
        assertEquals(null, exception.getMessage());
    }

    @Test
    public void testMessageConstructor() {
        String errorMessage = "Invalid User Request";
        InvalidUserRequest exception = new InvalidUserRequest(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testCustomErrorMessage() {
        String errorMessage = "This is a custom error message for the user.";
        InvalidUserRequest exception = new InvalidUserRequest(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}