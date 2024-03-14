package com.example.banking_transaction_service.exception;

import com.example.banking_transaction_service.response.TransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<TransactionResponse> handleAppException(AppException exception) {
        TransactionResponse apiResponse = new TransactionResponse();

        apiResponse.setCode(exception.getErrorCode().getCode());
        apiResponse.setMessage(exception.getMessage());
        apiResponse.setTransactionId(exception.getTransactionId());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
