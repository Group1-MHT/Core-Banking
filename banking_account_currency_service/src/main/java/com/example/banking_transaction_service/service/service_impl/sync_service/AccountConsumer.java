package com.example.banking_transaction_service.service.service_impl.sync_service;

import com.example.banking_transaction_service.dto.TransactionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AccountConsumer {


    private final Logger logger = LoggerFactory.getLogger(AccountConsumer.class);

    @KafkaListener(topics = "ask-balance-topic",groupId = "group-account",containerFactory = "transactionKafkaListenerContainerFactory")
    public void handleCrediting(ConsumerRecord<String, TransactionDTO> record) throws JsonProcessingException {
        TransactionDTO transactionDTO = record.value();
        System.out.println(transactionDTO.toString());
    }
}
