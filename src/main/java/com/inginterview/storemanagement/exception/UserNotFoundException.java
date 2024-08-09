package com.inginterview.storemanagement.exception;

public class UserNotFoundException extends UserDefinedAuthenticationException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
