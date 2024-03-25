package com.example.account_currency_service.service;

import com.example.account_currency_service.dto.constant.AccountType;
import com.example.account_currency_service.model.Account;
import com.example.account_currency_service.service.repository.AccountRepository;
import com.example.account_currency_service.service.service_impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.account_currency_service.dataset.Dataset.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccount() {
        Account account = newAccount;
        when(accountRepository.save(account)).thenReturn(account);

        Account createdAccount = accountService.createAccount(account);

        assertNotNull(createdAccount);
        assertEquals(account, createdAccount);
        assertEquals(account.getCreatedAt(), createdAccount.getCreatedAt());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void updateAccount() {
        Account account = testAccounts.get(0);
        account.setBalance(BigDecimal.ZERO);

        when(accountRepository.save(account)).thenReturn(account);

        Account updatedAccount = accountService.updateAccount(account);

        assertNotNull(updatedAccount);
        assertEquals(account, updatedAccount);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void deleteAccount() {
        Long accountId = anyLong();

        accountService.deleteAccount(accountId);

        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void getAccountById() {
        Account account = testAccounts.get(0);

        when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        when(accountRepository.findById(6L)).thenReturn(Optional.empty());

        Account foundAccount = accountService.getAccountById(account.getAccountId());
        Account notfoundAccount = accountService.getAccountById(6L);

        assertNotNull(foundAccount);
        assertNull(notfoundAccount);
        assertEquals(account, foundAccount);
        verify(accountRepository, times(1)).findById(account.getAccountId());
    }

    @Test
    void getAllAccounts() {
        when(accountRepository.findAll()).thenReturn(testAccounts);

        List<Account> foundAccounts = accountService.getAllAccounts();

        assertNotNull(foundAccounts);
        assertEquals(testAccounts.size(), foundAccounts.size());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void getAccountsByUserIdSuccess() {
        Long userId = 1L;
        List<Account> accounts = testAccounts.stream().filter(account -> account.getUserId().equals(userId)).toList();

        when(accountRepository.findByUserId(userId)).thenReturn(accounts);

        List<Account> foundAccounts = accountService.getAccountsByUserId(userId);

        assertNotNull(foundAccounts);
        assertEquals(accounts.size(), foundAccounts.size());
        assertTrue(accounts.containsAll(foundAccounts));
        verify(accountRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getAccountsByUserIdEmpty() {
        Long userId = 6L;

        when(accountRepository.findByUserId(userId)).thenReturn(List.of());

        List<Account> notfoundAccounts = accountService.getAccountsByUserId(userId);

        assertTrue(notfoundAccounts.isEmpty());
        verify(accountRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getAccountsByUserIdAndTypeSuccess() {
        Long userId = 1L;
        String accountType = AccountType.SAVINGS.name();
        List<Account> accounts = testAccounts.stream().filter(account -> account.getAccountType().equals(accountType)).toList();

        when(accountRepository.findByUserIdAndAccountType(userId, accountType)).thenReturn(accounts);

        List<Account> foundAccounts = accountService.getAccountsByUserIdAndType(userId, accountType);

        assertNotNull(foundAccounts);
        assertEquals(accounts.size(), foundAccounts.size());
        assertTrue(accounts.containsAll(foundAccounts));
        verify(accountRepository, times(1)).findByUserIdAndAccountType(userId, accountType);
    }

    @Test
    void getAccountsByUserIdAndTypeEmpty() {
        Long userId = 6L;
        String accountType = AccountType.SAVINGS.name();

        when(accountRepository.findByUserIdAndAccountType(userId, accountType)).thenReturn(List.of());

        List<Account> notfoundAccounts = accountService.getAccountsByUserIdAndType(userId, accountType);

        assertTrue(notfoundAccounts.isEmpty());
        verify(accountRepository, times(1)).findByUserIdAndAccountType(userId, accountType);
    }
}
