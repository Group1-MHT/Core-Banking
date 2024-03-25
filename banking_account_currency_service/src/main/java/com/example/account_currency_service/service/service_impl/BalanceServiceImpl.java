package com.example.account_currency_service.service.service_impl;

import com.example.account_currency_service.dto.constant.AccountType;
import com.example.account_currency_service.exception.ErrorCode;
import com.example.account_currency_service.exception.exception.TransactionException;
import com.example.account_currency_service.model.Account;
import com.example.account_currency_service.model.LatestSuccessTransaction;
import com.example.account_currency_service.service.repository.LastestSuccessTransactionRepository;
import com.example.account_currency_service.service.service_i.AccountService;
import com.example.account_currency_service.service.service_i.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

@Service
@Slf4j
public class BalanceServiceImpl implements BalanceService {
    @Autowired
    private AccountService accountService;

    @Autowired
    private LastestSuccessTransactionRepository lastestSuccessTransactionRepository;

    @Override
    @Transactional
    public void transfer(Long transactionId, Long sourceAccountId, Long destinationAccountId, BigDecimal amount) {
        Account sourceAccount = accountService.getAccountById(sourceAccountId);
        Account destinationAccount = accountService.getAccountById(destinationAccountId);
        if (!ObjectUtils.isEmpty(sourceAccount) && !ObjectUtils.isEmpty(destinationAccount)) {
            haveEnoughBalance(sourceAccount, amount);
            isInSameCurrency(sourceAccount, destinationAccount);
            isSpendAccount(sourceAccount);
            isSpendAccount(destinationAccount);
            sourceAccount.subtractMoney(amount);
            accountService.updateAccount(sourceAccount);

            destinationAccount.addMoney(amount);
            accountService.updateAccount(destinationAccount);

            lastestSuccessTransactionRepository.save(new LatestSuccessTransaction(6996, transactionId));
        } else {
            throw new TransactionException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
    }


    @Override
    @Transactional
    public void deposit(Long transactionId, Long destinationAccountId, BigDecimal amount) {
        Account destinationAccount = accountService.getAccountById(destinationAccountId);
        destinationAccount.addMoney(amount);
        accountService.updateAccount(destinationAccount);
        lastestSuccessTransactionRepository.save(new LatestSuccessTransaction(6996, transactionId));
    }

    @Override
    @Transactional
    public void withdraw(Long transactionId, Long sourceAccountId, BigDecimal amount) {
        Account sourceAccount = accountService.getAccountById(sourceAccountId);
        haveEnoughBalance(sourceAccount, amount);
        sourceAccount.subtractMoney(amount);
        accountService.updateAccount(sourceAccount);
        lastestSuccessTransactionRepository.save(new LatestSuccessTransaction(6996, transactionId));
    }


    private void haveEnoughBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new TransactionException(ErrorCode.ACCOUNT_NOT_ENOUGH_MONEY);
        }
    }

    private void isInSameCurrency(Account account1, Account account2) {
        if (!account1.getCurrency().getName().equals(account2.getCurrency().getName())) {
            throw new TransactionException(ErrorCode.CURRENCY_DIFFERENT);
        }
    }

    private void isSpendAccount(Account account) {
        if (!account.getAccountType().equals(AccountType.SPEND.name())) {
            throw new TransactionException(ErrorCode.SAVINGS_ACCOUNT_TRANSFER_NOT_SUPPORT);
        }
    }

}
