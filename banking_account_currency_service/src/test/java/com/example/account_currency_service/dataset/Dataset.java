package com.example.account_currency_service.dataset;

import com.example.account_currency_service.dto.AccountDTO;
import com.example.account_currency_service.dto.constant.AccountType;
import com.example.account_currency_service.model.Account;
import com.example.account_currency_service.model.Currency;
import com.example.account_currency_service.model.share.Role;
import com.example.account_currency_service.model.share.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Dataset {
    public static final List<Currency> testCurrencies = List.of(
            Currency.builder().currencyCode("USD").name("United States Dollar").symbol("$").build(),
            Currency.builder().currencyCode("EUR").name("Euro").symbol("€").build(),
            Currency.builder().currencyCode("GBP").name("British Pound").symbol("£").build()
    );

    public static final List<Account> testAccounts = List.of(
            Account.builder()
                    .accountId(1L).userId(1L)
                    .accountType(AccountType.SAVINGS.name())
                    .balance(BigDecimal.valueOf(2000))
                    .currency(testCurrencies.get(0))
                    .createdAt(LocalDateTime.now()).build(),
            Account.builder()
                    .accountId(2L).userId(1L)
                    .accountType(AccountType.SPEND.name())
                    .balance(BigDecimal.valueOf(1000))
                    .currency(testCurrencies.get(1))
                    .createdAt(LocalDateTime.now()).build(),
            Account.builder()
                    .accountId(3L).userId(1L)
                    .accountType(AccountType.SAVINGS.name())
                    .balance(BigDecimal.valueOf(0))
                    .currency(testCurrencies.get(0))
                    .createdAt(LocalDateTime.now()).build(),
            Account.builder()
                    .accountId(4L).userId(2L)
                    .accountType(AccountType.SAVINGS.name())
                    .balance(BigDecimal.valueOf(2000))
                    .currency(testCurrencies.get(0))
                    .createdAt(LocalDateTime.now()).build(),
            Account.builder()
                    .accountId(5L).userId(3L)
                    .accountType(AccountType.SPEND.name())
                    .balance(BigDecimal.valueOf(3000))
                    .currency(testCurrencies.get(1))
                    .createdAt(LocalDateTime.now()).build()
    );

    public static final User testUser = User.builder()
            .username("user")
            .userId(1L)
            .email("user@gmail.com")
            .fullName("The User")
            .password("********")
            .roles(Set.of(Role.builder().roleId(3L).name("User").build()))
            .createdAt(LocalDateTime.now())
            .build();

    public static final Account newAccount = Account.builder()
            .userId(2L)
            .accountType(AccountType.SAVINGS.name())
            .balance(BigDecimal.valueOf(200))
            .currency(testCurrencies.get(2))
            .createdAt(LocalDateTime.now())
            .build();

    public static final Currency newCurrency = Currency.builder().currencyCode("JPY").name("Japanese Yen").symbol("¥").build();

    public static final AccountDTO accountDTO = AccountDTO.builder()
            .userId(1L)
            .accountType(AccountType.SAVINGS)
            .balance(BigDecimal.valueOf(100))
            .currencyCode("USD")
            .build();
}
