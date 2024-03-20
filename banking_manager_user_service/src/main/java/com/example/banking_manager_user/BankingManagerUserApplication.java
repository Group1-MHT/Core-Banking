package com.example.banking_manager_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class BankingManagerUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankingManagerUserApplication.class, args);
    }

}
