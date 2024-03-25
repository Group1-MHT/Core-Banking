package com.example.banking_authorization_server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND( 404,"User not found"),
    ROLE_NOT_FOUND(404,"Role not found");

    private final int code;
    private final String message;
}
