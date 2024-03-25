package com.example.banking_authorization_server.service.client;

import com.example.banking_authorization_server.dto.UserDto;
import com.example.banking_authorization_server.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "user-manager", url = "http://localhost:9001/manager-user-service")
public interface UserClient {
    @PostMapping("/as/user/create")
    ResponseEntity<User> createUser(@RequestBody UserDto userDto);

    @GetMapping("/as/user/getById/{id}")
    ResponseEntity<User> getById(@PathVariable Integer id);
}