//package akdemy.edu.service_impl.sync_service;
//
//import akdemy.edu.model.Account;
//import akdemy.edu.response.TransactionResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.CompletableFuture;
//
//@Component
//public class BalanceLogProducer {
//
//    private final Logger logger = LoggerFactory.getLogger(BalanceLogProducer.class);
//
//    @Autowired
//    private KafkaTemplate<String, TransactionResponse> template;
//
//    public void TransactionConfirm(TransactionResponse TransactionResponse){
//        try {
//            CompletableFuture<SendResult<String, TransactionResponse>> future = template.send("transaction-topic", TransactionResponse).completable();
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
