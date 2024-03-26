package com.example.banking_transaction_service.service.impl;

import com.example.banking_transaction_service.dto.TransactionDTO;
import com.example.banking_transaction_service.model.Transaction;
import com.example.banking_transaction_service.model.TransactionStatus;
import com.example.banking_transaction_service.model.TransactionType;
import com.example.banking_transaction_service.repository.TransactionRepository;
import com.example.banking_transaction_service.service.BalanceClient;
import com.example.banking_transaction_service.service.mapper.IConverterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.banking_transaction_service.dataset.DataSet.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private BalanceClient balanceClient;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private IConverterDto<Transaction, TransactionDTO> transactionConverter;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {

    }


    @Test
    void canGetAccountTransactionHistory() {
        List<Transaction> transactionList = new ArrayList<>();
        List<TransactionDTO> transactionListDTO = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            transactionList.add(transferTransaction);
            transactionListDTO.add(transferTransactionDTO);
        }

        Page<Transaction> transactionPage = new PageImpl<Transaction>(transactionList, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"id")), 10);

        when(transactionRepository.findAccountHistoryTransaction(1L, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"id")))).thenReturn(transactionPage);
        when(transactionConverter.convertToListDto(transactionPage.toList())).thenReturn(transactionListDTO);
        //
        List<TransactionDTO> list = transactionService.getAccountTransactionHistory(1L, 0);
        //
        assertThat(list).isEqualTo(transactionListDTO);
        verify(transactionRepository).findAccountHistoryTransaction(1L, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"id")));
        verify(transactionConverter).convertToListDto(transactionPage.toList());
    }

    @Test
    void canGetTransactionById() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.ofNullable(transferTransaction));
        when(transactionConverter.convertToDto(transferTransaction)).thenReturn(transferTransactionDTO);
        //
        TransactionDTO transactionDTO = transactionService.getTransactionById(1L);
        //
        assertThat(transactionDTO).isEqualTo(transferTransactionDTO);
        verify(transactionRepository).findById(1L);
        verify(transactionConverter).convertToDto(transferTransaction);
    }

    @Test
    void canTransfer() {
        //arrange
        when(balanceClient.transferBalance(transferTransactionDTO)).thenReturn(ResponseEntity.ok(successResponse));
        when(transactionConverter.convertToEntity(transferTransactionDTO)).thenReturn(transferTransaction);
        when(transactionRepository.save(transferTransaction)).thenReturn(transferTransaction);
        //act
        TransactionDTO transactionDTO = transactionService.transfer(transferTransactionDTO);
        //assert
        assertThat(transactionDTO).isEqualTo(transferTransactionDTO);
        verify(balanceClient).transferBalance(transferTransactionDTO);
        verify(transactionConverter).convertToEntity(transferTransactionDTO);
        verify(transactionRepository).save(transferTransaction);
    }

    @Test

    void canWithdraw() {
        //arrange
        when(balanceClient.withdrawBalance(withdrawTransactionDTO)).thenReturn(ResponseEntity.ok(successResponse));
        when(transactionConverter.convertToEntity(withdrawTransactionDTO)).thenReturn(withdrawTransaction);
        when(transactionRepository.save(withdrawTransaction)).thenReturn(withdrawTransaction);
        //act
        TransactionDTO transactionDTO = transactionService.withdraw(withdrawTransactionDTO);
        //assert
        assertThat(transactionDTO).isEqualTo(withdrawTransactionDTO);
        verify(balanceClient).withdrawBalance(withdrawTransactionDTO);
        verify(transactionConverter).convertToEntity(withdrawTransactionDTO);
        verify(transactionRepository).save(withdrawTransaction);
    }

    @Test
    void canDeposit() {
        //arrange
        when(balanceClient.depositBalance(depositTransactionDTO)).thenReturn(ResponseEntity.ok(successResponse));
        when(transactionConverter.convertToEntity(depositTransactionDTO)).thenReturn(depositTransaction);
        when(transactionRepository.save(depositTransaction)).thenReturn(depositTransaction);
        //act
        TransactionDTO transactionDTO = transactionService.deposit(depositTransactionDTO);
        //assert
        assertThat(transactionDTO).isEqualTo(depositTransactionDTO);
        verify(balanceClient).depositBalance(depositTransactionDTO);
        verify(transactionConverter).convertToEntity(depositTransactionDTO);
        verify(transactionRepository).save(depositTransaction);
    }

    @Test
    void canInitTransaction() {
        //arrange
        when(transactionConverter.convertToEntity(transferTransactionDTO)).thenReturn(transferTransaction);
        when(transactionRepository.save(transferTransaction)).thenReturn(processTransaction);
        when(transactionConverter.convertToDto(processTransaction)).thenReturn(processTransactionDTO);
        //act
        TransactionDTO transactionDTO = transactionService.initTransaction(transferTransactionDTO);
        //assert
        assertThat(transactionDTO).isEqualTo(processTransactionDTO);
        verify(transactionRepository).save(transferTransaction);
        verify(transactionConverter).convertToEntity(transferTransactionDTO);
        verify(transactionConverter).convertToDto(processTransaction);
    }
}