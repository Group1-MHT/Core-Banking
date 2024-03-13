package com.example.banking_transaction_service.service.sync_service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AskBalanceProducer {

    @Autowired
    private KafkaTemplate<String,Long> template;

    public void askBalance(Long transactionId){
        try {
            CompletableFuture<SendResult<String, Long>> future = template.send("ask-balance-topic", transactionId).completable();
            future.whenComplete((result, error) -> {
                if (error == null) {
                    log.info("Sent success with offset " + result.getRecordMetadata().offset());
                } else {
                    log.error("Fail to send " + error.getMessage());
                }
            });
        } catch (Exception e){
            log.error("ERROR : " + e.getMessage());
        }
    }
}
