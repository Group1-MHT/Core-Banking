package com.account_currency_service.dto.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    TRANSFER(1),

    DEPOSIT(2),

    WITHDRAW(3);

    private final int code;

}
