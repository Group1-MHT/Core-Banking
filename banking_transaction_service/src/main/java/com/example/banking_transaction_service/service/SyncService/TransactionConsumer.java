package com.example.banking_transaction_service.service.SyncService;

import com.example.banking_transaction_service.dto.TransactionDTO;
import com.example.banking_transaction_service.model.Transaction;
import com.example.banking_transaction_service.model.TransactionStatus;
import com.example.banking_transaction_service.repository.TransactionRepository;
import com.example.banking_transaction_service.response.TransactionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    private Logger logger = LoggerFactory.getLogger(TransactionConsumer.class);
    @Autowired
    private TransactionRepository transactionRepository;

    @KafkaListener(topics = "transaction-topic",groupId = "group-transaction",containerFactory = "transactionKafkaListenerContainerFactory")
    public void handleTransactionResponse(ConsumerRecord<String, TransactionResponse> record) throws JsonProcessingException {
        logger.info("receive message from balancer service");
        TransactionResponse transactionResponse = record.value();
        Transaction transaction = transactionRepository.findById(transactionResponse.getTransactionId()).get();
        if (transactionResponse.getCode() == 200){
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        } else {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
        }
        transactionRepository.save(transaction);
        logger.info("handle transaction done");
    }

}

