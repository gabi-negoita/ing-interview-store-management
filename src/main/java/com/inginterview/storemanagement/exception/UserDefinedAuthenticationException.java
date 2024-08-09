package com.inginterview.storemanagement.exception;

public class UserDefinedAuthenticationException extends RuntimeException {

    public UserDefinedAuthenticationException(String message) {
        super(message);
    }

    public UserDefinedAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
