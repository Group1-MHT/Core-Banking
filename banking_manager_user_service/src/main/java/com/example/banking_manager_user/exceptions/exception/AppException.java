package com.example.banking_manager_user.exceptions.exception;

import com.example.banking_manager_user.exceptions.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AppException extends RuntimeException{
    private ErrorCode errorCode;
    public AppException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
