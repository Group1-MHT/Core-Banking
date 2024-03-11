package akdemy.edu.service_impl;

import akdemy.edu.Exception.AppException;
import akdemy.edu.Exception.ErrorCode;
import akdemy.edu.dto.TransactionDto;
import akdemy.edu.model.Account;
import akdemy.edu.model.Balance;
import akdemy.edu.model.Currency;
import akdemy.edu.repository.AccountRepository;
import akdemy.edu.repository.BalanceRepository;
import akdemy.edu.response.ApiResponse;
import akdemy.edu.service_i.AccountService;
import akdemy.edu.service_i.BalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BalanceServiceImpl implements BalanceService {

    private static Logger logger = LoggerFactory.getLogger(BalanceServiceImpl.class);

    private final BalanceRepository balanceRepository;


    public  BalanceServiceImpl(BalanceRepository balanceRepository){
        this.balanceRepository = balanceRepository;
    }

    @Override
    public void createBalance(Account account, Currency currency){
        Balance accountBalance = Balance.builder()
                    .accountId(account.getAccountId())
                    .balance(BigDecimal.valueOf(0))
                    .currency(currency)
                    .build();
        balanceRepository.save(accountBalance);
    }

    @Override
    public Balance getBalance(Long accountId){
        Balance balance = balanceRepository.findById(accountId).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        return balance;
    }

    @Override
    @Transactional
    public void tranfer(Long transactionId, Long sourceAccountId, Long destinationAccountId, BigDecimal amount){
        this.withdraw(transactionId,sourceAccountId,amount);
        this.deposit(transactionId,destinationAccountId,amount);
    }


    @Override
    @Transactional
    public void deposit(Long transactionId, Long destinationAccountId, BigDecimal amount){
        Balance balance = this.getBalance(destinationAccountId);
        balance.addMoney(amount);
        balance.setLatestTransactionId(transactionId);
        logger.error(balance.toString());
        balanceRepository.save(balance);
    }

    @Override
    @Transactional
    public void withdraw(Long transactionId, Long sourceAccountId, BigDecimal amount){
        Balance balance = this.getBalance(sourceAccountId);
        isBalanceEnough(balance,amount);
        balance.subtractMoney(amount);
        balance.setLatestTransactionId(transactionId);
        logger.error(balance.getBalance().subtract(amount).toString());
        balanceRepository.save(balance);
    }


    private void isBalanceEnough(Balance balance, BigDecimal amount){
        if (balance.getBalance().compareTo(amount) < 0){
            throw new AppException(ErrorCode.ACCOUNT_NOT_ENOUGH_MONEY);
        }
    }

}
