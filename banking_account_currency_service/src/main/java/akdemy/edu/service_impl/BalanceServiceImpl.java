package akdemy.edu.service_impl;

import akdemy.edu.exception.AppException;
import akdemy.edu.exception.ErrorCode;
import akdemy.edu.exception.TransactionException;
import akdemy.edu.model.Balance;
import akdemy.edu.repository.BalanceRepository;
import akdemy.edu.service_i.BalanceService;
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
        this.withdraw(transactionId, sourceAccountId, amount);
        this.deposit(transactionId, destinationAccountId, amount);
    }


    @Override
    @Transactional
    public void deposit(Long transactionId, Long destinationAccountId, BigDecimal amount) {
        Balance balance = this.getBalance(destinationAccountId);
        balance.addMoney(amount);
        balance.setLatestTransactionId(transactionId);
        balanceRepository.save(balance);
    }

    @Override
    @Transactional
    public void withdraw(Long transactionId, Long sourceAccountId, BigDecimal amount) {
        Balance balance = this.getBalance(sourceAccountId);
        isBalanceEnough(balance, amount);
        balance.subtractMoney(amount);
        balance.setLatestTransactionId(transactionId);
        balanceRepository.save(balance);
    }


    private void isBalanceEnough(Balance balance, BigDecimal amount) {
        if (balance.getBalance().compareTo(amount) < 0) {
            throw new TransactionException(ErrorCode.ACCOUNT_NOT_ENOUGH_MONEY);
        }
    }

}
