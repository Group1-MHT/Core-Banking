package com.example.banking_transaction_service.service.mapper.impl;

import com.example.banking_transaction_service.dto.TransactionDto;
import com.example.banking_transaction_service.model.Transaction;
import com.example.banking_transaction_service.service.mapper.AbstractDtoMapperAdapter;

public class TransactionMapper extends AbstractDtoMapperAdapter<Transaction, TransactionDto> {

    public TransactionMapper(Class<Transaction> classParameter, Class<TransactionDto> classDtoParameter){
        super(classParameter,classDtoParameter);
    }
}
