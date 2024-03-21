package com.example.account_currency_service.service.service_impl;

import com.example.account_currency_service.exception.ErrorCode;
import com.example.account_currency_service.exception.exception.TransactionException;
import com.example.account_currency_service.model.Balance;
import com.example.account_currency_service.model.LatestSuccessTransaction;
import com.example.account_currency_service.service.repository.LastestSuccessTransactionRepository;
import com.example.account_currency_service.service.service_i.BalanceService;
import com.example.account_currency_service.service.repository.BalanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class BalanceServiceImpl implements BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private LastestSuccessTransactionRepository lastestSuccessTransactionRepository;

    @Override
    public void createBalance(Balance balance) {
        balanceRepository.save(balance);
    }

    @Override
    public void deleteBalance(Long accountId) {
        balanceRepository.deleteById(accountId);
    }

    @Override
    public Balance getBalance(Long accountId) {
        return balanceRepository.findById(accountId).orElseThrow(() -> new TransactionException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Override
    @Transactional
    public void transfer(Long transactionId, Long sourceAccountId, Long destinationAccountId, BigDecimal amount) {
        Balance sourceAccountbalance = this.getBalance(sourceAccountId);
        isBalanceEnough(sourceAccountbalance, amount);
        sourceAccountbalance.subtractMoney(amount);
        balanceRepository.save(sourceAccountbalance);
        Balance destinationAccountbalance = this.getBalance(destinationAccountId);
        destinationAccountbalance.addMoney(amount);
        balanceRepository.save(destinationAccountbalance);
        lastestSuccessTransactionRepository.save(new LatestSuccessTransaction(6996,transactionId));
    }


    @Override
    @Transactional
    public void deposit(Long transactionId, Long destinationAccountId, BigDecimal amount) {
        Balance balance = this.getBalance(destinationAccountId);
        balance.addMoney(amount);
        balanceRepository.save(balance);
        lastestSuccessTransactionRepository.save(new LatestSuccessTransaction(6996,transactionId));
    }

    @Override
    @Transactional
    public void withdraw(Long transactionId, Long sourceAccountId, BigDecimal amount) {
        Balance balance = this.getBalance(sourceAccountId);
        isBalanceEnough(balance, amount);
        balance.subtractMoney(amount);
        balanceRepository.save(balance);
        lastestSuccessTransactionRepository.save(new LatestSuccessTransaction(6996,transactionId));
    }


    private void isBalanceEnough(Balance balance, BigDecimal amount) {
        if (balance.getBalance().compareTo(amount) < 0) {
            throw new TransactionException(ErrorCode.ACCOUNT_NOT_ENOUGH_MONEY);
        }
    }

}
