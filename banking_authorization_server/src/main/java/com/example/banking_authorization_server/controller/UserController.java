package com.example.banking_authorization_server.controller;

import com.example.banking_authorization_server.dto.MessageDto;
import com.example.banking_authorization_server.dto.UserDto;
import com.example.banking_authorization_server.entity.User;
import com.example.banking_authorization_server.repository.UserRepository;
import com.example.banking_authorization_server.service.UserService;
import com.example.banking_authorization_server.service.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserClient userClient;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> createUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/getInfoUser/{id}")
    public User getInfoUser(@PathVariable Integer id){
        User user = userClient.getById(id).getBody();
        return userRepository.save(user);
    }
}
