package com.folksdev.workshop.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAlreadyExistExceptionTest {
    @Test
    void testEmptyConstructor() {
        UserAlreadyExistException exception = new UserAlreadyExistException();
        assertEquals(null, exception.getMessage());
    }

    @Test
    void testMessageConstructor() {
        String errorMessage = "User Already Exists";
        UserAlreadyExistException exception = new UserAlreadyExistException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testCustomErrorMessage() {
        String errorMessage = "Custom error message for User Already Exists";
        UserAlreadyExistException exception = new UserAlreadyExistException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}