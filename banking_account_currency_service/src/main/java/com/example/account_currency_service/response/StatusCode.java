package com.example.account_currency_service.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

    WITHDRAW_SUCCESS(200, "WITHDRAW SUCCESS"),

    DEPOSIT_SUCCESS(200, "DEPOSIT SUCCESS"),

    TRANSFER_SUCCESS(200, "TRANSFER SUCCESS");

    private final int code;

    private final String message;
}
