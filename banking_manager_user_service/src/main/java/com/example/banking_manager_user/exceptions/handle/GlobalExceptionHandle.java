package com.example.banking_manager_user.exceptions.handle;

import com.example.banking_manager_user.exceptions.exception.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> handleAppException(AppException appException){
        return ResponseEntity.status(appException.getErrorCode().getCode()).body(appException.getMessage());
    }
}
