package com.example.banking_transaction_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ACCOUNT_NOT_FOUND(1001, "Account not found."),

    ACCOUNT_NOT_ENOUGH_MONEY(1002, "There is not enough money in account."),

    CURRENCY_NOT_FOUND(404, "Currency has not existed!"),

    DUPLICATED_CURRENCY(409, "Currency has existed!"),

    USER_NOT_FOUND(404, "User not found!");

    private final int code;

    private final String message;
}
