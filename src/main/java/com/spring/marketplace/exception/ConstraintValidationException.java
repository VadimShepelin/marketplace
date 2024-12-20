package com.spring.marketplace.exception;

public class ConstraintValidationException extends RuntimeException{
    public ConstraintValidationException(String message) {
        super(message);
    }
}
