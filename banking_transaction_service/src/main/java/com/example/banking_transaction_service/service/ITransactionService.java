package com.example.banking_transaction_service.service;


import com.example.banking_transaction_service.dto.TransactionDTO;

public interface ITransactionService {

    TransactionDTO transfer(TransactionDTO transactionDto);

    TransactionDTO deposit(TransactionDTO transactionDto);

    TransactionDTO withdraw(TransactionDTO transactionDto);


    TransactionDTO initTransaction(TransactionDTO transactionDto);
}
