package com.example.banking_transaction_service.exception.handler;

import com.example.banking_transaction_service.exception.exception.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> handleAppException(AppException exception) {
        return ResponseEntity.status(exception.getErrorCode().getCode()).body(exception.getMessage());
    }
}
