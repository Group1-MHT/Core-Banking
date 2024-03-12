package com.example.banking_transaction_service.exception;

import com.example.banking_transaction_service.response.TransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<TransactionResponse> handleAppException(TransactionException exception) {
        TransactionResponse transactionResponse = new TransactionResponse();

        transactionResponse.setCode(exception.getErrorCode().getCode());
        transactionResponse.setMessage(exception.getMessage());

        return ResponseEntity.ok().body(transactionResponse);
    }
}
