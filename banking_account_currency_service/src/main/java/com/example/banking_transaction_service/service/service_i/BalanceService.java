package com.example.banking_transaction_service.service.service_i;

import com.example.banking_transaction_service.model.Balance;

import java.math.BigDecimal;

public interface BalanceService {

    Balance getBalance(Long accountId);

    void withdraw(Long transactionId, Long sourceAccountId, BigDecimal amount);

    void deposit(Long transactionId, Long destinationAccountId, BigDecimal amount);

    void transfer(Long transactionId, Long sourceAccountId, Long destinationAccountId, BigDecimal amount);

    void createBalance(Balance balance);

    void deleteBalance(Long accountId);

}
