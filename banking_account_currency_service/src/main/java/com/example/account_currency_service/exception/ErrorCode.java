package com.example.account_currency_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    ACCOUNT_NOT_FOUND(404, "Account not found."),

    SOURCE_ACCOUNT_NOT_FOUND(404, "Source account not found."),

    DESTINATION_ACCOUNT_NOT_FOUND(404, "Destination account not found."),

    ACCOUNT_NOT_ENOUGH_MONEY(404, "There is not enough money in account."),

    CURRENCY_NOT_FOUND(404, "Currency has not existed!"),

    USER_NOT_FOUND(404, "User not found!"),

    CURRENCY_DUPLICATED(409, "Currency has existed!"),

    SAVINGS_ACCOUNT_TRANSFER_NOT_SUPPORT(500, "Cannot transfer to/by savings account."),

    CURRENCY_DIFFERENT(500, "Different currencies not supported!");

    private final int code;

    private final String message;
}
