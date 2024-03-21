package com.example.banking_authorization_server;

import com.example.banking_authorization_server.entity.Role;
import com.example.banking_authorization_server.entity.RoleName;
import com.example.banking_authorization_server.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingAuthorizationServerApplication  /*implements  CommandLineRunner*/{

//	@Autowired
//	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(BankingAuthorizationServerApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		Role adminRole = Role.builder().role(RoleName.Admin).build();
//		Role userRole = Role.builder().role(RoleName.User).build();
//		Role staffRole = Role.builder().role(RoleName.Staff).build();
//		roleRepository.save(adminRole);
//		roleRepository.save(userRole);
//		roleRepository.save(staffRole);
//	}
}
