package com.example.account_currency_service.exception.exception;

import com.example.account_currency_service.exception.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionException extends RuntimeException {

    private ErrorCode errorCode;


    public TransactionException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
