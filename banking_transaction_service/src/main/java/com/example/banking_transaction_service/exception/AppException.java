package com.example.banking_transaction_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException{

    private ErrorCode errorCode;

    private Long transactionId;

    public AppException(ErrorCode errorCode,Long transactionId) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.transactionId = transactionId;
    }
}
