package com.example.account_currency_service.service.service_i;

import java.math.BigDecimal;

public interface BalanceService {
    void deposit(Long id, Long destinationAccountId, BigDecimal amount);

    void withdraw(Long id, Long sourceAccountId, BigDecimal amount);

    void transfer(Long id, Long sourceAccountId, Long destinationAccountId, BigDecimal amount);
}
