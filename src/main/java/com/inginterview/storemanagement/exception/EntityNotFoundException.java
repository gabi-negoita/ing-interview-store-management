package com.inginterview.storemanagement.exception;

public class EntityNotFoundException extends UserDefinedRestControllerException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
