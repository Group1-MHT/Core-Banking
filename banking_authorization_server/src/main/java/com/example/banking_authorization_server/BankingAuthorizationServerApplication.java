package com.example.banking_authorization_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class BankingAuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingAuthorizationServerApplication.class, args);
	}

}
