package com.spring.marketplace.utils.enums;

public enum ErrorType {
    PRODUCT_NOT_FOUND("No such product"),
    NO_PRODUCTS_FOUND("No products found"),
    PRODUCT_DONT_EXISTS("Product dont exists"),
    UNIQUE_CONSTRAINT_EXCEPTION("A product with the same sku already exists");


    private final String message;

    ErrorType(String message) {
        this.message = message;
    };


    public String getMessage() {
        return message;
    }
}