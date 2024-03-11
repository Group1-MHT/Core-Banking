package com.example.banking_transaction_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    TRANFER(1),

    DEPOSIT(2),

    WITHDRAW(3);

    private final int code;

}
