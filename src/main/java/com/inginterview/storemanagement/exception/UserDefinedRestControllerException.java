package com.inginterview.storemanagement.exception;

public class UserDefinedRestControllerException extends RuntimeException {

    public UserDefinedRestControllerException(String message) {
        super(message);
    }

    public UserDefinedRestControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
