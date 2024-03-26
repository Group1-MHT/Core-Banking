package com.example.account_currency_service.controller;

import com.example.account_currency_service.dto.AccountDTO;
import com.example.account_currency_service.dto.constant.AccountType;
import com.example.account_currency_service.exception.ErrorCode;
import com.example.account_currency_service.exception.exception.AppException;
import com.example.account_currency_service.model.Account;
import com.example.account_currency_service.service.client.UserClient;
import com.example.account_currency_service.service.service_i.AccountService;
import com.example.account_currency_service.service.service_i.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.account_currency_service.dataset.Dataset.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private AccountController accountController;

    @Test
    void testCreateAccountSuccess() {
        AccountDTO accountDTO = AccountDTO.builder()
                .userId(1L)
                .accountType(AccountType.SAVINGS)
                .balance(BigDecimal.valueOf(2000))
                .currencyCode("USD")
                .build();
        Account account = Account.builder()
                .accountId(1L).userId(1L)
                .accountType(AccountType.SAVINGS.name())
                .balance(BigDecimal.valueOf(1000))
                .currency(testCurrencies.get(0))
                .createdAt(LocalDateTime.now()).build();

        when(userClient.getById(1)).thenReturn(ResponseEntity.ok(testUser));
        when(currencyService.getCurrencyByCode("USD")).thenReturn(testCurrencies.get(0));
        when(accountService.createAccount(any(Account.class))).thenReturn(account);

        ResponseEntity<?> response = accountController.createAccount(accountDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    void testCreateAccountUserNotFound() {
        AccountDTO accountDTO = AccountDTO.builder()
                .userId(6L)
                .accountType(AccountType.SAVINGS)
                .balance(BigDecimal.valueOf(2000))
                .currencyCode("USD")
                .build();
        when(userClient.getById(6)).thenReturn(ResponseEntity.internalServerError().build());

        assertThrows(AppException.class, () -> accountController.createAccount(accountDTO), ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    void testCreateAccountCurrencyNotFound() {
        AccountDTO accountDTO = AccountDTO.builder()
                .userId(1L)
                .accountType(AccountType.SAVINGS)
                .balance(BigDecimal.valueOf(2000))
                .currencyCode("?")
                .build();
        when(userClient.getById(1)).thenReturn(ResponseEntity.ok(testUser));
        when(currencyService.getCurrencyByCode("?")).thenReturn(null);

        assertThrows(AppException.class, () -> accountController.createAccount(accountDTO), ErrorCode.CURRENCY_NOT_FOUND.getMessage());
    }

    @Test
    void testGetAccountById() {
        Long accountId = 1L;
        Account account = testAccounts.get(0);
        when(accountService.getAccountById(accountId)).thenReturn(account);

        ResponseEntity<?> response = accountController.getAccountById(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    void testGetAllAccountsByUserId() {
        Long userId = 1L;
        List<Account> userAccounts = testAccounts.stream().filter(account -> account.getUserId().equals(userId)).toList();
        when(accountService.getAccountsByUserId(userId)).thenReturn(userAccounts);

        ResponseEntity<?> response = accountController.getAllAccountsByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userAccounts, response.getBody());
    }

    @Test
    void testUpdateAccountSuccess() {
        Long accountId = 1L;
        AccountDTO accountDTO = AccountDTO.builder()
                .userId(1L)
                .accountType(AccountType.SAVINGS)
                .balance(BigDecimal.valueOf(2000))
                .currencyCode("USD")
                .build();
        Account updatedAccount = Account.builder()
                .accountId(accountId)
                .userId(accountDTO.getUserId())
                .accountType(accountDTO.getAccountType().name())
                .balance(accountDTO.getBalance())
                .currency(testCurrencies.get(0))
                .build();

        when(userClient.getById(1)).thenReturn(ResponseEntity.ok(testUser));
        when(accountService.getAccountById(accountId)).thenReturn(testAccounts.get(0));
        when(currencyService.getCurrencyByCode("USD")).thenReturn(testCurrencies.get(0));
        when(accountService.updateAccount(any(Account.class))).thenReturn(updatedAccount);

        ResponseEntity<?> response = accountController.updateAccount(accountId, accountDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAccount, response.getBody());
    }

    @Test
    void testUpdateAccountUserNotFound() {
        Long accountId = 1L;
        AccountDTO accountDTO = AccountDTO.builder()
                .userId(6L)
                .accountType(AccountType.SAVINGS)
                .balance(BigDecimal.valueOf(2000))
                .currencyCode("USD")
                .build();

        when(userClient.getById(6)).thenReturn(ResponseEntity.internalServerError().build());

        assertThrows(AppException.class, () -> accountController.updateAccount(accountId, accountDTO), ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    void testUpdateAccountNotFound() {
        Long accountId = 6L;
        AccountDTO accountDTO = AccountDTO.builder()
                .userId(1L)
                .accountType(AccountType.SAVINGS)
                .balance(BigDecimal.valueOf(2000))
                .currencyCode("USD")
                .build();

        when(userClient.getById(1)).thenReturn(ResponseEntity.ok(testUser));
        when(accountService.getAccountById(accountId)).thenReturn(null);

        assertThrows(AppException.class, () -> accountController.updateAccount(accountId, accountDTO), ErrorCode.ACCOUNT_NOT_FOUND.getMessage());
    }

    @Test
    void testUpdateAccountCurrencyNotFound() {
        Long accountId = 1L;
        AccountDTO accountDTO = AccountDTO.builder()
                .userId(1L)
                .accountType(AccountType.SAVINGS)
                .balance(BigDecimal.valueOf(2000))
                .currencyCode("?")
                .build();

        when(userClient.getById(1)).thenReturn(ResponseEntity.ok(testUser));
        when(accountService.getAccountById(accountId)).thenReturn(testAccounts.get(0));
        when(currencyService.getCurrencyByCode("?")).thenReturn(null);

        assertThrows(AppException.class, () -> accountController.updateAccount(accountId, accountDTO), ErrorCode.CURRENCY_NOT_FOUND.getMessage());
    }

    @Test
    void testDeleteAccount() {
        Long accountId = anyLong();

        accountController.deleteAccount(accountId);

        verify(accountService, times(1)).deleteAccount(accountId);
    }

    @Test
    void testGetAllAccounts() {
        List<Account> allAccounts = testAccounts;

        when(accountService.getAllAccounts()).thenReturn(allAccounts);

        ResponseEntity<?> response = accountController.getAllAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allAccounts, response.getBody());
    }
}
