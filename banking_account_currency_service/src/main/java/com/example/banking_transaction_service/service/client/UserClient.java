package com.example.banking_transaction_service.service.client;

import com.example.banking_transaction_service.model.share.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-manager", url = "http://localhost:9001/manager-user-service")
public interface UserClient {
    @GetMapping("/as/user/getById/{id}")
    ResponseEntity<User> getById(@PathVariable Integer id);
}
