package com.account_currency_service.exception.handler;

import com.account_currency_service.exception.exception.TransactionException;
import com.account_currency_service.response.TransactionResponse;
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
