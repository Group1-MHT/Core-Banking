package com.example.banking_authorization_server.controller;

import com.example.banking_authorization_server.dto.ClientDto;
import com.example.banking_authorization_server.dto.MessageDto;
import com.example.banking_authorization_server.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor

public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> CreateClient(@RequestBody ClientDto clientDto){
        return new ResponseEntity<>(clientService.createClient(clientDto), HttpStatus.CREATED);
    }
}
