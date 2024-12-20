package com.spring.marketplace.exception;

public class ProductDontHaveIdException extends RuntimeException{

    public ProductDontHaveIdException(String message) {
        super(message);
    }
}
