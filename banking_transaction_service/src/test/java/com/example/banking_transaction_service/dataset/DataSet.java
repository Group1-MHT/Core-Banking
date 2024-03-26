package com.example.banking_transaction_service.dataset;

import com.example.banking_transaction_service.dto.TransactionDTO;
import com.example.banking_transaction_service.model.Transaction;
import com.example.banking_transaction_service.model.TransactionStatus;
import com.example.banking_transaction_service.model.TransactionType;
import com.example.banking_transaction_service.response.TransactionResponse;

import java.math.BigDecimal;

public class DataSet {

    public static Transaction transferTransaction = Transaction.builder().sourceAccountId(1L)
            .destinationAccountId(2L)
            .transactionType(TransactionType.TRANSFER)
            .transactionStatus(TransactionStatus.SUCCESS)
            .amount(BigDecimal.ONE)
            .build();
    public static TransactionDTO transferTransactionDTO = TransactionDTO.builder().sourceAccountId(1L)
            .destinationAccountId(2L)
            .transactionType(TransactionType.TRANSFER)
            .transactionStatus(TransactionStatus.SUCCESS)
            .amount(BigDecimal.ONE)
            .build();

    public static Transaction depositTransaction = Transaction.builder().sourceAccountId(1L)
            .destinationAccountId(2L)
            .transactionType(TransactionType.DEPOSIT)
            .transactionStatus(TransactionStatus.SUCCESS)
            .amount(BigDecimal.ONE)
            .build();
    public static TransactionDTO depositTransactionDTO = TransactionDTO.builder().sourceAccountId(1L)
            .destinationAccountId(2L)
            .transactionType(TransactionType.DEPOSIT)
            .transactionStatus(TransactionStatus.SUCCESS)
            .amount(BigDecimal.ONE)
            .build();

    public static Transaction withdrawTransaction = Transaction.builder().sourceAccountId(1L)
            .destinationAccountId(2L)
            .transactionType(TransactionType.WITHDRAW)
            .transactionStatus(TransactionStatus.SUCCESS)
            .amount(BigDecimal.ONE)
            .build();
    public static TransactionDTO withdrawTransactionDTO = TransactionDTO.builder().sourceAccountId(1L)
            .destinationAccountId(2L)
            .transactionType(TransactionType.WITHDRAW)
            .transactionStatus(TransactionStatus.SUCCESS)
            .amount(BigDecimal.ONE)
            .build();

    public static TransactionDTO processTransactionDTO = TransactionDTO.builder().sourceAccountId(1L)
            .destinationAccountId(2L)
            .transactionType(TransactionType.TRANSFER)
            .transactionStatus(TransactionStatus.IN_PROCESS)
            .amount(BigDecimal.ONE)
            .build();

    public static Transaction processTransaction = Transaction.builder().sourceAccountId(1L)
            .destinationAccountId(2L)
            .transactionType(TransactionType.TRANSFER)
            .transactionStatus(TransactionStatus.IN_PROCESS)
            .amount(BigDecimal.ONE)
            .build();

    public static TransactionResponse successResponse = new TransactionResponse(200, "Transaction successful", 1L);
    public static TransactionResponse sourceAccountNotFoundResponse = new TransactionResponse(404, "Source account not found.", 1L);

    public static TransactionResponse destinationAccountNotFoundResponse = new TransactionResponse(404, "Destination account not found.", 1L);

    public static TransactionResponse accountNotEnoughMoneyResponse = new TransactionResponse(404, "There is not enough money in account.", 1L);

    public static TransactionResponse currencyNotFoundResponse = new TransactionResponse(404, "Currency has not existed!", 1L);
}
