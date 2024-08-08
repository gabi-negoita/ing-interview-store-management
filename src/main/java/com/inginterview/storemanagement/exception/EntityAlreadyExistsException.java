package com.inginterview.storemanagement.exception;

public class EntityAlreadyExistsException extends UserDefinedRestControllerException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
