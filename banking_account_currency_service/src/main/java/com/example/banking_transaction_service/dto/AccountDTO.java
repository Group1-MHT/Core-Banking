package com.example.banking_transaction_service.dto;

import com.example.banking_transaction_service.dto.constant.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long userId;
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;
    private BigDecimal balance;
    private String currencyCode;
}
