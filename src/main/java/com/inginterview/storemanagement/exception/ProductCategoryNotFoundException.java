package com.inginterview.storemanagement.exception;

public class ProductCategoryNotFoundException extends UserDefinedRestControllerException {

    public ProductCategoryNotFoundException(String message) {
        super(message);
    }

    public ProductCategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
