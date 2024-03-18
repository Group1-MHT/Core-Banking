package com.example.banking_transaction_service.service.service_impl.sync_service;

import com.example.banking_transaction_service.dto.TransactionDTO;
import com.example.banking_transaction_service.response.TransactionResponse;
import com.example.banking_transaction_service.service.repository.BalanceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountConsumer {

    private final BalanceRepository balanceRepository;

    private final BinLogService binLogService;

    private KafkaTemplate<String, TransactionResponse> transactionResponseKafkaTemplate;


    private final Logger logger = LoggerFactory.getLogger(AccountConsumer.class);

    public AccountConsumer(BalanceRepository balanceRepository,
                           BinLogService binLogService,
                           @Qualifier("transactionResponseKafkaTemplate") KafkaTemplate<String, TransactionResponse> transactionResponseKafkaTemplate) {
        this.balanceRepository = balanceRepository;
        this.binLogService = binLogService;
        this.transactionResponseKafkaTemplate = transactionResponseKafkaTemplate;
    }

    @KafkaListener(topics = "ask-balance-topic",groupId = "group-account",containerFactory = "transactionKafkaListenerContainerFactory")
    public void handleAskingTransaction(ConsumerRecord<String, TransactionDTO> record) throws JsonProcessingException {
        logger.info("receive message ask from transaction service");
        TransactionDTO transactionDTO = record.value();
        String startTime = transactionDTO.getCreatedAt().toString();
        String stopTime = transactionDTO.getCreatedAt().plusMinutes(1L).toString();
        String fileName = (String) balanceRepository.getMasterStatus().get("File");
        boolean checkTransactionSuccess = binLogService.ReadBinlog(fileName,startTime,stopTime, transactionDTO.getId());
        if (checkTransactionSuccess){
            transactionResponseKafkaTemplate.send("transaction-topic",new TransactionResponse(200,"transaction already handle success", transactionDTO.getId()));
        } else {
            transactionResponseKafkaTemplate.send("transaction-topic",new TransactionResponse(404,"not-found", transactionDTO.getId()));
        }
        logger.info("handle message ask from transaction service done!");
    }

}
