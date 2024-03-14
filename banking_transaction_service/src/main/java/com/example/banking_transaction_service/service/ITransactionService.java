package com.example.banking_transaction_service.service;


import com.example.banking_transaction_service.dto.TransactionDTO;

import java.util.Collection;

public interface ITransactionService {

    Collection<TransactionDTO> getAccountTransactionHistory(Long accountId, int pageNumber);

    TransactionDTO getTransactionById(Long transactionId);

    TransactionDTO transfer(TransactionDTO transactionDto);

    TransactionDTO deposit(TransactionDTO transactionDto);

    TransactionDTO withdraw(TransactionDTO transactionDto);


    TransactionDTO initTransaction(TransactionDTO transactionDto);
}
