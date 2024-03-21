package com.example.banking_authorization_server.service;

import com.example.banking_authorization_server.dto.ClientDto;
import com.example.banking_authorization_server.dto.MessageDto;
import com.example.banking_authorization_server.entity.Client;

public interface ClientService {
    Client clientFromDto(ClientDto clientDto);
    MessageDto createClient(ClientDto clientDto);
}
