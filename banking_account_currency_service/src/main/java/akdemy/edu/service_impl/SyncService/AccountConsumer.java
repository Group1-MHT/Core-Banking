package akdemy.edu.service_impl.SyncService;

import akdemy.edu.model.Account;
import akdemy.edu.service_i.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AccountConsumer {

    @Autowired
    private AccountService accountService;

    private final Logger logger = LoggerFactory.getLogger(AccountConsumer.class);

    @KafkaListener(topics = "account-topic",groupId = "")
    public void consume(String message){
        logger.info(message);
    }
}
