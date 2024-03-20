package com.example.balancelogservice;

import com.example.balancelogservice.Service.ReadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication
public class BalanceLogServiceApplication implements CommandLineRunner {

    @Autowired
    private  ReadLogService readLogService;


    public static void main(String[] args) {
        SpringApplication.run(BalanceLogServiceApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        this.readLogService.ReadLog();
    }

}
