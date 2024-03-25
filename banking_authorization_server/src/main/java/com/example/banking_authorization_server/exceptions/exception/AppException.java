package com.example.banking_authorization_server.exceptions.exception;

import com.example.banking_authorization_server.exceptions.ErrorCode;
import lombok.Data;

@Data
public class AppException extends RuntimeException{
    private ErrorCode errorCode;
    public AppException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
