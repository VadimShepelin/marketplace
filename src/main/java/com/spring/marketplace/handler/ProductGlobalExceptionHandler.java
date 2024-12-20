package com.spring.marketplace.handler;

import com.spring.marketplace.exception.NoSuchProductException;
import com.spring.marketplace.exception.ProductIncorrectData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ProductGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ProductIncorrectData> handleNoSuchProductException(NoSuchProductException ex) {
        ProductIncorrectData incorrectData = new ProductIncorrectData();
        incorrectData.setInfo(ex.getMessage());

        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ProductIncorrectData> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ProductIncorrectData incorrectData = new ProductIncorrectData();
        incorrectData.setInfo(ex.getMessage());

        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }
}
