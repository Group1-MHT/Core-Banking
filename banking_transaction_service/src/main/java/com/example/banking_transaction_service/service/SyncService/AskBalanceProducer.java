//package com.example.banking_transaction_service.service.SyncService;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.CompletableFuture;
//
//@Service
//public class AskBalanceProducer {
//
//    private final Logger logger = LoggerFactory.getLogger(AskBalanceProducer.class);
//
//    @Autowired
//    private KafkaTemplate<String,Long> template;
//
//    public void AskBalance(Long transactionId){
//        try {
//            CompletableFuture<SendResult<String, Long>> future = template.send("ask-balance-topic", transactionId).completable();
//            future.whenComplete((result, error) -> {
//                if (error == null) {
//                    logger.info("Sent success with offset" + result.getRecordMetadata().offset());
//                } else {
//                    logger.error("Fail to send " + error.getMessage());
//
//                }
//            });
//        } catch (Exception e){
//            logger.error("ERROR : "+ e.getMessage());
//        }
//    }
//}
