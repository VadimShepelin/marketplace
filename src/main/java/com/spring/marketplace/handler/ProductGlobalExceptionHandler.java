package com.spring.marketplace.handler;

import com.spring.marketplace.exception.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;


@ControllerAdvice
public class ProductGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDetails.builder()
                        .exceptionClass(ex.getClass().getName())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}
