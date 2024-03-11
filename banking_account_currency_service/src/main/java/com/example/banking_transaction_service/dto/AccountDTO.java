package com.example.banking_transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long userId;
    private String accountType;
    private BigDecimal balance;
    private String currencyCode;
}
