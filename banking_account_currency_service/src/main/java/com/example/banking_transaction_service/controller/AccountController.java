package com.example.banking_transaction_service.controller;

import com.example.banking_transaction_service.dto.AccountDTO;
import com.example.banking_transaction_service.exception.exception.AppException;
import com.example.banking_transaction_service.exception.ErrorCode;
import com.example.banking_transaction_service.model.Account;
import com.example.banking_transaction_service.model.Balance;
import com.example.banking_transaction_service.model.Currency;
import com.example.banking_transaction_service.service.client.UserClient;
import com.example.banking_transaction_service.service.service_i.AccountService;
import com.example.banking_transaction_service.service.service_i.BalanceService;
import com.example.banking_transaction_service.service.service_i.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account-currency-service")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private UserClient userClient;

    @PostMapping("/asu/account")
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO accountDTO) {
        if (!ObjectUtils.isEmpty(userClient.getById(Math.toIntExact(accountDTO.getUserId())).getBody())) {
            Currency currency = currencyService.getCurrencyByCode(accountDTO.getCurrencyCode());
            if (!ObjectUtils.isEmpty(currency)) {
                Account newAccount = accountService.createAccount(Account.builder()
                        .userId(accountDTO.getUserId())
                        .accountType(accountDTO.getAccountType().name())
                        .build());
                Balance balance = Balance.builder()
                        .accountId(newAccount.getAccountId())
                        .balance(accountDTO.getBalance())
                        .currency(currency)
                        .build();
                balanceService.createBalance(balance);
                return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully!");
            } else {
                throw new AppException(ErrorCode.CURRENCY_NOT_FOUND);
            }
        } else {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
    }

    @GetMapping("/asu/account/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable("accountId") Long accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @GetMapping("/asu/account/user/{userId}")
    public ResponseEntity<?> getAllAccountsByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }

    @PutMapping("/as/account/{accountId}")
    public ResponseEntity<?> updateAccount(@PathVariable("accountId") Long accountId, @RequestBody AccountDTO
            accountDTO) {
        if (!ObjectUtils.isEmpty(userClient.getById(Math.toIntExact(accountDTO.getUserId())).getBody())) {
            if (!ObjectUtils.isEmpty(accountService.getAccountById(accountId))) {
                if (!ObjectUtils.isEmpty(currencyService.getCurrencyByCode(accountDTO.getCurrencyCode()))) {
                    accountService.updateAccount(Account.builder()
                            .accountId(accountId)
                            .userId(accountDTO.getUserId())
                            .accountType(accountDTO.getAccountType().name())
                            .build());
                    Balance balance = balanceService.getBalance(accountId);
                    if (balance.getBalance().compareTo(accountDTO.getBalance()) < 0) {
                        balanceService.deposit(null, accountId, accountDTO.getBalance());
                    } else {
                        balanceService.withdraw(null, accountId, accountDTO.getBalance());
                    }
                    return ResponseEntity.ok("Account updated successfully!");
                } else {
                    throw new AppException(ErrorCode.CURRENCY_NOT_FOUND);
                }
            } else {
                throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
            }
        } else {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
    }

    @DeleteMapping("/as/account/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountId) {
        balanceService.deleteBalance(accountId);
        accountService.deleteAccount(accountId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/as/accounts")
    public ResponseEntity<?> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}