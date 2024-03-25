package com.example.account_currency_service.dto;

import com.example.account_currency_service.dto.constant.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private Long userId;
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;
    private BigDecimal balance;
    private String currencyCode;
}
