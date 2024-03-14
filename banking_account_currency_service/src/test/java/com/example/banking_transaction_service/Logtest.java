package com.example.banking_transaction_service;

import com.example.banking_transaction_service.repository.BalanceRepository;
import com.example.banking_transaction_service.response.TransactionResponse;
import com.example.banking_transaction_service.service_impl.SyncService.BinLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.Rollback;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Rollback(value = false)
public class Logtest {

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BinLogService binLogService;

    @Autowired
    @Qualifier("transactionResponseKafkaTemplate")
    private KafkaTemplate<String, TransactionResponse> transactionResponseKafkaTemplate;

    @Test
    public void logTest(){
        Map<String,Object> masterStatus = balanceRepository.getMasterStatus();
        System.out.println(masterStatus.get("File"));
        String fileName = (String) masterStatus.get("File");
        String start = "2024-03-12 08:00:15";
        String stop = "2024-03-12 08:01:15";
        boolean check = this.binLogService.ReadBinlog(fileName,start,stop,103L);
        if (check){
            System.out.println("Transaction sucess");
        } else System.out.println("fail");

        assertNotNull(balanceRepository.getMasterStatus());
    }

    @Test
    public void setTransactionResponseKafkaTest(){
        assertNotNull(transactionResponseKafkaTemplate.send("transaction-topic",new TransactionResponse(200,"cc",100L)));
    }

}
