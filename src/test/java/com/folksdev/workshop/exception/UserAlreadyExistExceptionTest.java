package com.folksdev.workshop.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAlreadyExistExceptionTest {
    @Test
    public void testEmptyConstructor() {
        UserAlreadyExistException exception = new UserAlreadyExistException();
        assertEquals(null, exception.getMessage());
    }

    @Test
    public void testMessageConstructor() {
        String errorMessage = "User Already Exists";
        UserAlreadyExistException exception = new UserAlreadyExistException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testCustomErrorMessage() {
        String errorMessage = "Custom error message for User Already Exists";
        UserAlreadyExistException exception = new UserAlreadyExistException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}