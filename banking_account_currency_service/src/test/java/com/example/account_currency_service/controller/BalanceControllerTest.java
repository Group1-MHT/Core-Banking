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

    @InjectMocks
    private BalanceController balanceController;

    @Test
    void testDepositSuccess() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .destinationAccountId(2L)
                .amount(BigDecimal.valueOf(300))
                .build();
        doNothing().when(balanceService).deposit(transactionDTO.getId(), transactionDTO.getDestinationAccountId(), transactionDTO.getAmount());

        ResponseEntity<TransactionResponse> response = balanceController.depositBalance(transactionDTO);

        assertEquals(HttpStatus.valueOf(StatusCode.DEPOSIT_SUCCESS.getCode()), response.getStatusCode());
        assertEquals(StatusCode.DEPOSIT_SUCCESS.getCode(),response.getBody().getCode());
        assertEquals(StatusCode.DEPOSIT_SUCCESS.getMessage(),response.getBody().getMessage());
        assertEquals(transactionDTO.getId(), response.getBody().getTransactionId());
        verify(balanceService, times(1)).deposit(transactionDTO.getId(), transactionDTO.getDestinationAccountId(), transactionDTO.getAmount());
    }

    @Test
    void testWithdrawSuccess() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .sourceAccountId(3L)
                .amount(BigDecimal.valueOf(100))
                .build();
        doNothing().when(balanceService).withdraw(transactionDTO.getId(), transactionDTO.getSourceAccountId(), transactionDTO.getAmount());

        ResponseEntity<TransactionResponse> response = balanceController.withdrawBalance(transactionDTO);

        assertEquals(HttpStatus.valueOf(StatusCode.WITHDRAW_SUCCESS.getCode()), response.getStatusCode());
        assertEquals(StatusCode.WITHDRAW_SUCCESS.getCode(),response.getBody().getCode());
        assertEquals(StatusCode.WITHDRAW_SUCCESS.getMessage(),response.getBody().getMessage());
        assertEquals(transactionDTO.getId(), response.getBody().getTransactionId());
        verify(balanceService, times(1)).withdraw(transactionDTO.getId(), transactionDTO.getSourceAccountId(), transactionDTO.getAmount());
    }

    @Test
    void testTransferSuccess() throws InterruptedException {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .sourceAccountId(1L)
                .destinationAccountId(4L)
                .amount(BigDecimal.valueOf(100))
                .build();

        doNothing().when(balanceService).transfer(transactionDTO.getId(), transactionDTO.getSourceAccountId(), transactionDTO.getDestinationAccountId(), transactionDTO.getAmount());

        ResponseEntity<TransactionResponse> response = balanceController.transferBalance(transactionDTO);

        assertEquals(HttpStatus.valueOf(StatusCode.TRANSFER_SUCCESS.getCode()), response.getStatusCode());
        assertEquals(StatusCode.TRANSFER_SUCCESS.getCode(),response.getBody().getCode());
        assertEquals(StatusCode.TRANSFER_SUCCESS.getMessage(),response.getBody().getMessage());
        assertEquals(transactionDTO.getId(), response.getBody().getTransactionId());
        verify(balanceService, times(1)).transfer(transactionDTO.getId(), transactionDTO.getSourceAccountId(), transactionDTO.getDestinationAccountId(), transactionDTO.getAmount());
    }
}
