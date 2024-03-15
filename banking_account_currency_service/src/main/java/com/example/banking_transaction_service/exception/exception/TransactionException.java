package com.example.banking_transaction_service.exception.exception;

import com.example.banking_transaction_service.exception.ErrorCode;
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
