package com.spring.marketplace.exception;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public class ErrorDetails {
    private String exceptionClass;
    private String message;
    private LocalDateTime timestamp;
}
