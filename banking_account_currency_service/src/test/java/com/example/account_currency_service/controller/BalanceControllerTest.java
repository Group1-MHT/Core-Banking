package com.example.account_currency_service.controller;

import com.example.account_currency_service.dto.TransactionDTO;
import com.example.account_currency_service.dto.constant.AccountType;
import com.example.account_currency_service.exception.ErrorCode;
import com.example.account_currency_service.exception.exception.TransactionException;
import com.example.account_currency_service.model.Account;
import com.example.account_currency_service.model.Currency;
import com.example.account_currency_service.model.LatestSuccessTransaction;
import com.example.account_currency_service.response.StatusCode;
import com.example.account_currency_service.response.TransactionResponse;
import com.example.account_currency_service.service.repository.LastestSuccessTransactionRepository;
import com.example.account_currency_service.service.service_i.AccountService;
import com.example.account_currency_service.service.service_i.BalanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static com.example.account_currency_service.dataset.Dataset.testAccounts;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceControllerTest {

    @Mock
    private BalanceService balanceService;

    @Mock
    private AccountService accountService;

    @Mock
    private LastestSuccessTransactionRepository lastestSuccessTransactionRepository;

    @InjectMocks
    private BalanceController balanceController;

    @Test
    void testDeposit() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(anyLong())
                .destinationAccountId(2L)
                .amount(BigDecimal.valueOf(300))
                .build();
        Account destinationAccount = testAccounts.get(2);
        when(accountService.getAccountById(2L)).thenReturn(destinationAccount);
        doNothing().when(balanceService).deposit(transactionDTO.getId(), transactionDTO.getDestinationAccountId(), transactionDTO.getAmount());

        ResponseEntity<TransactionResponse> response = balanceController.depositBalance(transactionDTO);

        assertEquals(HttpStatus.valueOf(StatusCode.DEPOSIT_SUCCESS.getCode()), response.getStatusCode());
        assertEquals(new TransactionResponse(StatusCode.DEPOSIT_SUCCESS.getCode(), StatusCode.DEPOSIT_SUCCESS.getMessage(), transactionDTO.getId()), response.getBody());
        verify(accountService, times(1)).getAccountById(2L);
        verify(balanceService, times(1)).deposit(transactionDTO.getId(), transactionDTO.getDestinationAccountId(), transactionDTO.getAmount());
        verify(lastestSuccessTransactionRepository, times(1)).save(any(LatestSuccessTransaction.class));
    }

    @Test
    void testWithdrawSuccess() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(anyLong())
                .sourceAccountId(3L)
                .amount(BigDecimal.valueOf(100))
                .build();
        Account sourceAccount = testAccounts.get(3);
        when(accountService.getAccountById(3L)).thenReturn(sourceAccount);
        doNothing().when(balanceService).withdraw(transactionDTO.getId(), transactionDTO.getSourceAccountId(), transactionDTO.getAmount());

        ResponseEntity<TransactionResponse> response = balanceController.withdrawBalance(transactionDTO);

        assertEquals(HttpStatus.valueOf(StatusCode.WITHDRAW_SUCCESS.getCode()), response.getStatusCode());
        assertEquals(new TransactionResponse(StatusCode.WITHDRAW_SUCCESS.getCode(), StatusCode.WITHDRAW_SUCCESS.getMessage(), transactionDTO.getId()), response.getBody());
        verify(accountService, times(1)).getAccountById(3L);
        verify(balanceService, times(1)).withdraw(transactionDTO.getId(), transactionDTO.getSourceAccountId(), transactionDTO.getAmount());
        verify(lastestSuccessTransactionRepository, times(1)).save(any(LatestSuccessTransaction.class));
    }

    @Test
    void testTransferSuccess() throws InterruptedException {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .sourceAccountId(1L)
                .destinationAccountId(4L)
                .amount(BigDecimal.valueOf(100))
                .build();

        Account sourceAccount = testAccounts.get(1);
        Account destinationAccount = testAccounts.get(4);

        when(accountService.getAccountById(1L)).thenReturn(sourceAccount);
        when(accountService.getAccountById(4L)).thenReturn(destinationAccount);
        doNothing().when(balanceService).transfer(transactionDTO.getId(), transactionDTO.getSourceAccountId(), transactionDTO.getDestinationAccountId(), transactionDTO.getAmount());

        ResponseEntity<TransactionResponse> response = balanceController.transferBalance(transactionDTO);

        assertEquals(HttpStatus.valueOf(StatusCode.TRANSFER_SUCCESS.getCode()), response.getStatusCode());
        assertEquals(new TransactionResponse(StatusCode.TRANSFER_SUCCESS.getCode(), StatusCode.TRANSFER_SUCCESS.getMessage(), transactionDTO.getId()), response.getBody());
        verify(accountService, times(1)).getAccountById(1L);
        verify(accountService, times(1)).getAccountById(4L);
        verify(balanceService, times(1)).transfer(transactionDTO.getId(), transactionDTO.getSourceAccountId(), transactionDTO.getDestinationAccountId(), transactionDTO.getAmount());
        verify(lastestSuccessTransactionRepository, times(1)).save(any(LatestSuccessTransaction.class));
    }

    @Test
    void testWithdrawBalanceInsufficientBalance() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .sourceAccountId(1L)
                .amount(BigDecimal.valueOf(10000))
                .build();

        Account sourceAccount = testAccounts.get(1);

        when(accountService.getAccountById(1L)).thenReturn(sourceAccount);

        assertThrows(TransactionException.class, () -> balanceController.withdrawBalance(transactionDTO), ErrorCode.ACCOUNT_NOT_ENOUGH_MONEY.getMessage());
    }

    @Test
    void testDepositBalanceAccountNotFound() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .destinationAccountId(1L)
                .amount(BigDecimal.valueOf(100))
                .build();

        when(accountService.getAccountById(1L)).thenReturn(null);

        assertThrows(TransactionException.class, () -> balanceController.depositBalance(transactionDTO), ErrorCode.ACCOUNT_NOT_FOUND.getMessage());
    }

    @Test
    void testWithdrawBalanceAccountNotFound() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .sourceAccountId(1L)
                .amount(BigDecimal.valueOf(100))
                .build();

        when(accountService.getAccountById(1L)).thenReturn(null);

        assertThrows(TransactionException.class, () -> balanceController.withdrawBalance(transactionDTO), ErrorCode.ACCOUNT_NOT_FOUND.getMessage());
    }

    @Test
    void transferBalanceSourceAccountNotFound() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .sourceAccountId(1L)
                .destinationAccountId(6L)
                .amount(BigDecimal.valueOf(100))
                .build();

        when(accountService.getAccountById(1L)).thenReturn(testAccounts.get(0));
        when(accountService.getAccountById(6L)).thenReturn(null);

        assertThrows(TransactionException.class, () -> balanceController.transferBalance(transactionDTO), ErrorCode.ACCOUNT_NOT_FOUND.getMessage());
    }

    @Test
    void transferBalanceDestinationAccountNotFound() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.valueOf(100))
                .build();

        Account sourceAccount = Account.builder()
                .accountId(1L)
                .userId(1L)
                .accountType(AccountType.SPEND.name())
                .balance(BigDecimal.valueOf(1000))
                .currency(Currency.builder().currencyCode("USD").name("United States Dollar").symbol("$").build())
                .build();

        when(accountService.getAccountById(1L)).thenReturn(sourceAccount);
        when(accountService.getAccountById(2L)).thenReturn(null);

        assertThrows(TransactionException.class, () -> balanceController.transferBalance(transactionDTO), ErrorCode.ACCOUNT_NOT_FOUND.getMessage());
    }

    @Test
    void transferBalanceCurrencyMismatch() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.valueOf(100))
                .build();

        Account sourceAccount = Account.builder()
                .accountId(1L)
                .userId(1L)
                .accountType(AccountType.SPEND.name())
                .balance(BigDecimal.valueOf(1000))
                .currency(Currency.builder().currencyCode("USD").name("United States Dollar").symbol("$").build())
                .build();

        Account destinationAccount = Account.builder()
                .accountId(2L)
                .userId(1L)
                .accountType(AccountType.SPEND.name())
                .balance(BigDecimal.valueOf(1000))
                .currency(Currency.builder().currencyCode("EUR").name("Euro").symbol("€").build())
                .build();

        when(accountService.getAccountById(1L)).thenReturn(sourceAccount);
        when(accountService.getAccountById(2L)).thenReturn(destinationAccount);

        assertThrows(TransactionException.class, () -> balanceController.transferBalance(transactionDTO), ErrorCode.CURRENCY_DIFFERENT.getMessage());
    }

    @Test
    void transferBalanceSourceAccountTypeMismatch() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.valueOf(100))
                .build();

        Account sourceAccount = Account.builder()
                .accountId(1L)
                .userId(1L)
                .accountType(AccountType.SAVINGS.name())
                .balance(BigDecimal.valueOf(1000))
                .currency(Currency.builder().currencyCode("USD").name("United States Dollar").symbol("$").build())
                .build();

        Account destinationAccount = Account.builder()
                .accountId(2L)
                .userId(1L)
                .accountType(AccountType.SPEND.name())
                .balance(BigDecimal.valueOf(1000))
                .currency(Currency.builder().currencyCode("USD").name("United States Dollar").symbol("$").build())
                .build();

        when(accountService.getAccountById(1L)).thenReturn(sourceAccount);
        when(accountService.getAccountById(2L)).thenReturn(destinationAccount);

        assertThrows(TransactionException.class, () -> balanceController.transferBalance(transactionDTO), ErrorCode.SAVINGS_ACCOUNT_TRANSFER_NOT_SUPPORT.getMessage());
    }

    @Test
    void transferBalanceDestinationAccountTypeMismatch() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .sourceAccountId(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.valueOf(100))
                .build();

        Account sourceAccount = Account.builder()
                .accountId(1L)
                .userId(1L)
                .accountType(AccountType.SPEND.name())
                .balance(BigDecimal.valueOf(1000))
                .currency(Currency.builder().currencyCode("USD").name("United States Dollar").symbol("$").build())
                .build();

        Account destinationAccount = Account.builder()
                .accountId(2L)
                .userId(1L)
                .accountType(AccountType.SAVINGS.name())
                .balance(BigDecimal.valueOf(1000))
                .currency(Currency.builder().currencyCode("USD").name("United States Dollar").symbol("$").build())
                .build();

        when(accountService.getAccountById(1L)).thenReturn(sourceAccount);
        when(accountService.getAccountById(2L)).thenReturn(destinationAccount);

        assertThrows(TransactionException.class, () -> balanceController.transferBalance(transactionDTO), ErrorCode.SAVINGS_ACCOUNT_TRANSFER_NOT_SUPPORT.getMessage());
    }


}
