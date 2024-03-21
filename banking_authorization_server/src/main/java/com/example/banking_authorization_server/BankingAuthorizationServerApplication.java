package com.example.banking_authorization_server;

import com.example.banking_authorization_server.entity.Role;
import com.example.banking_authorization_server.entity.RoleName;
import com.example.banking_authorization_server.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingAuthorizationServerApplication  {

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BankingAuthorizationServerApplication.class, args);
	}

}
