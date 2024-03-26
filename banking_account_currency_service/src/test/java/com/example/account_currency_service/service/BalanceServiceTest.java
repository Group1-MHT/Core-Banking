package com.example.account_currency_service.service;

import com.example.account_currency_service.exception.exception.TransactionException;
import com.example.account_currency_service.model.Account;
import com.example.account_currency_service.model.LatestSuccessTransaction;
import com.example.account_currency_service.service.repository.LastestSuccessTransactionRepository;
import com.example.account_currency_service.service.service_i.AccountService;
import com.example.account_currency_service.service.service_impl.BalanceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.example.account_currency_service.dataset.Dataset.testAccounts;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private LastestSuccessTransactionRepository lastestSuccessTransactionRepository;

    @InjectMocks
    private BalanceServiceImpl balanceService;

    @Test
    void testTransferSuccess() {
        Long sourceAccountId = 1L;
        Long destinationAccountId = 4L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Account sourceAccount = testAccounts.get(1);
        Account destinationAccount = testAccounts.get(4);

        when(accountService.getAccountById(sourceAccountId)).thenReturn(sourceAccount);
        when(accountService.getAccountById(destinationAccountId)).thenReturn(destinationAccount);

        balanceService.transfer(anyLong(), sourceAccountId, destinationAccountId, amount);

        verify(accountService, times(1)).getAccountById(sourceAccountId);
        verify(accountService, times(1)).getAccountById(destinationAccountId);
        verify(accountService, times(1)).updateAccount(sourceAccount);
        verify(accountService, times(1)).updateAccount(destinationAccount);
        verify(lastestSuccessTransactionRepository, times(1)).save(any(LatestSuccessTransaction.class));
    }

    @Test
    void testTransferInsufficientBalance() {
        Long sourceAccountId = 1L;
        Long destinationAccountId = 4L;
        BigDecimal amount = BigDecimal.valueOf(300);
        Account sourceAccount = testAccounts.get(1);
        Account destinationAccount = testAccounts.get(4);

        when(accountService.getAccountById(sourceAccountId)).thenReturn(sourceAccount);
        when(accountService.getAccountById(destinationAccountId)).thenReturn(destinationAccount);

        assertThrows(TransactionException.class, () -> balanceService.transfer(anyLong(), sourceAccountId, destinationAccountId, amount));

        verify(accountService, times(1)).getAccountById(sourceAccountId);
        verify(accountService, times(1)).getAccountById(destinationAccountId);
        verify(accountService, times(0)).updateAccount(any(Account.class));
        verify(lastestSuccessTransactionRepository, times(0)).save(any(LatestSuccessTransaction.class));
    }

    @Test
    void testTransferDifferentCurrency() {
        Long sourceAccountId = 1L;
        Long destinationAccountId = 3L;
        BigDecimal amount = BigDecimal.valueOf(300);
        Account sourceAccount = testAccounts.get(1);
        Account destinationAccount = testAccounts.get(3);

        when(accountService.getAccountById(sourceAccountId)).thenReturn(sourceAccount);
        when(accountService.getAccountById(destinationAccountId)).thenReturn(destinationAccount);

        assertThrows(TransactionException.class, () -> balanceService.transfer(anyLong(), sourceAccountId, destinationAccountId, amount));

        verify(accountService, times(1)).getAccountById(sourceAccountId);
        verify(accountService, times(1)).getAccountById(destinationAccountId);
        verify(accountService, times(0)).updateAccount(any(Account.class));
        verify(lastestSuccessTransactionRepository, times(0)).save(any(LatestSuccessTransaction.class));
    }

    @Test
    void testDeposit() {
        Long destinationAccountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(300);
        Account destinationAccount = testAccounts.get(2);

        when(accountService.getAccountById(destinationAccountId)).thenReturn(destinationAccount);

        balanceService.deposit(anyLong(), destinationAccountId, amount);

        verify(accountService, times(1)).getAccountById(destinationAccountId);
        verify(accountService, times(1)).updateAccount(destinationAccount);
        verify(lastestSuccessTransactionRepository, times(1)).save(any(LatestSuccessTransaction.class));
    }

    @Test
    void testWithdrawSuccess() {
        Long sourceAccountId = 3L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Account sourceAccount = testAccounts.get(3);

        when(accountService.getAccountById(sourceAccountId)).thenReturn(sourceAccount);

        balanceService.withdraw(anyLong(), sourceAccountId, amount);

        verify(accountService, times(1)).getAccountById(sourceAccountId);
        verify(accountService, times(1)).updateAccount(sourceAccount);
        verify(lastestSuccessTransactionRepository, times(1)).save(any(LatestSuccessTransaction.class));
    }

    @Test
    void testWithdrawInsufficientBalance() {
        Long sourceAccountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(300);
        Account sourceAccount = testAccounts.get(1);

        when(accountService.getAccountById(sourceAccountId)).thenReturn(sourceAccount);

        assertThrows(TransactionException.class, () -> balanceService.withdraw(anyLong(), sourceAccountId, amount));

        verify(accountService, times(1)).getAccountById(sourceAccountId);
        verify(accountService, times(0)).updateAccount(any(Account.class));
        verify(lastestSuccessTransactionRepository, times(0)).save(any(LatestSuccessTransaction.class));
    }

    @Test
    void testTransferNotSpendAccounts() {
        Long sourceAccountId = 1L;
        Long destinationAccountId = 4L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Account sourceAccount = testAccounts.get(1);
        Account destinationAccount = testAccounts.get(4);

        when(accountService.getAccountById(sourceAccountId)).thenReturn(sourceAccount);
        when(accountService.getAccountById(destinationAccountId)).thenReturn(destinationAccount);

        assertThrows(TransactionException.class, () -> balanceService.transfer(anyLong(), sourceAccountId, destinationAccountId, amount));

        verify(accountService, times(1)).getAccountById(sourceAccountId);
        verify(accountService, times(1)).getAccountById(destinationAccountId);
        verify(accountService, times(0)).updateAccount(sourceAccount);
        verify(accountService, times(0)).updateAccount(destinationAccount);
        verify(lastestSuccessTransactionRepository, times(0)).save(any(LatestSuccessTransaction.class));
    }

}