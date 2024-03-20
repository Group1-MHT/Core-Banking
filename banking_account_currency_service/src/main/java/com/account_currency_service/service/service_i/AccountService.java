package com.account_currency_service.service.service_i;

import com.account_currency_service.model.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);

    Account updateAccount(Account account);

    void deleteAccount(Long accountId);

    Account getAccountById(Long accountId);

    List<Account> getAccountsByUserId(Long userId);

    List<Account> getAccountsByUserIdAndType(Long userId, String accountType);

    List<Account> getAllAccounts();
}
