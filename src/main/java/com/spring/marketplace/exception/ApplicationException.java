package com.spring.marketplace.exception;

import java.util.EnumMap;
import java.util.Map;

public class ApplicationException extends RuntimeException {
    private final ErrorType errorType;

    public enum ErrorType {
        PRODUCT_NOT_FOUND("No such product"),
        PRODUCT_ALREADY_EXISTS("Product already exists");
        private final String message;

        ErrorType(String message) {
            this.message = message;
        };


        public String getMessage() {
            return message;
        }
    }

    public ApplicationException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
