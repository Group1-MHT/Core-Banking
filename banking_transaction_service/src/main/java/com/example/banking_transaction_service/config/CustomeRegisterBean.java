package com.example.banking_transaction_service.config;


import com.example.banking_transaction_service.dto.TransactionDto;
import com.example.banking_transaction_service.model.Transaction;
import com.example.banking_transaction_service.service.mapper.impl.TransactionMapper;
import feign.Client;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomeRegisterBean {


//    @Bean
//    public Client feignClient() {
//        return new Client.Default(null, null);
//    }


    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean(name = "transactionMapper")
    public TransactionMapper getTransactionMapper(){
        return new TransactionMapper(Transaction.class, TransactionDto.class);
    }

}