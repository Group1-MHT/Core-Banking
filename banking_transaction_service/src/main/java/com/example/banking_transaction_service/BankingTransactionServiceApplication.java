package com.example.banking_transaction_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class BankingTransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingTransactionServiceApplication.class, args);
	}

}
