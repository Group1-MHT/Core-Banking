package com.example.banking_transaction_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NO_TRANSACTION_HISTORY(001,"You dont have transaction history"),

    TRANSACTION_NOT_FOUND(002,"Transaction has not found"),


    ACCOUNT_NOT_FOUND(1001, "Account has not found."),

    NOT_ENOUGH_MONEY_IN_ACCOUNT(1002, "There is not enough money in account."),

    TRANSACTION_FAIL(1003, "TRANSACTION FAIL."),


    TRANSACTIONTYPE_NOT_SUPPORTED(2001, "Transaction type not supported.");


    private final int code;

    private final String message;

}
