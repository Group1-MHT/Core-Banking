//package com.example.banking_transaction_service.service.sync_service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AccountConsumer {
//
//    private final Logger logger = LoggerFactory.getLogger(AccountConsumer.class);
//
//    @KafkaListener(topics = "account-topic",groupId = "")
//    public void consume(String message){
//        logger.info(message);
//    }
//}
