package com.example.banking_transaction_service.service;


import com.example.banking_transaction_service.dto.TransactionDto;

public interface ITransactionService {

    TransactionDto tranfer(TransactionDto transactionDto);

    TransactionDto deposit(TransactionDto transactionDto);

    TransactionDto withdraw(TransactionDto transactionDto);


    TransactionDto initTransaction(TransactionDto transactionDto);
}
