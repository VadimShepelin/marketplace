package com.spring.marketplace.handler;

import com.spring.marketplace.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ProductGlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProductIncorrectData> handleRuntimeExceptions(RuntimeException ex) {
        ProductIncorrectData incorrectData = new ProductIncorrectData();
        incorrectData.setInfo(ex.getMessage());

        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }



}
