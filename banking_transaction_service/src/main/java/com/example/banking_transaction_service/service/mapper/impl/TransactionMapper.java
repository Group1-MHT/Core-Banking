package com.example.banking_transaction_service.service.mapper.impl;

import com.example.banking_transaction_service.dto.TransactionDTO;
import com.example.banking_transaction_service.model.Transaction;
import com.example.banking_transaction_service.service.mapper.AbstractDtoMapperAdapter;

public class TransactionMapper extends AbstractDtoMapperAdapter<Transaction, TransactionDTO> {

    public TransactionMapper(Class<Transaction> classParameter, Class<TransactionDTO> classDtoParameter){
        super(classParameter,classDtoParameter);
    }
}
