package akdemy.edu.service_impl.SyncService;

import akdemy.edu.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AccountProducer {

    private final Logger logger = LoggerFactory.getLogger(AccountConsumer.class);

    @Autowired
    private KafkaTemplate<String,Object> template;

    public void sendAccountToTrasactionService(Account account){
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("account-topic", account).completable();
            future.whenComplete((result, error) -> {
                if (error == null) {
                    logger.info("Sent Account success with offset" + result.getRecordMetadata().offset());
                } else {
                    logger.error("Fail to send Account" + error.getMessage());

                }
            });
        } catch (Exception e){
            logger.error("ERROR : "+ e.getMessage());
        }
    }
}
