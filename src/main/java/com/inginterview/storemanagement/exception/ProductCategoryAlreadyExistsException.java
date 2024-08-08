package com.inginterview.storemanagement.exception;

public class ProductCategoryAlreadyExistsException extends EntityAlreadyExistsException {

    public ProductCategoryAlreadyExistsException(String message) {
        super(message);
    }

    public ProductCategoryAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
