package com.inginterview.storemanagement.exception;

public class ProductNotFoundException extends UserDefinedRestControllerException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
