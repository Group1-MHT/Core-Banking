package com.example.balancelogservice;

import com.example.balancelogservice.Service.ReadLogService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BalanceLogServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test(){
        ReadLogService readLog = new ReadLogService();
    }

}
