package com.example.banking_authorization_server.serviceImpl;

import com.example.banking_authorization_server.dto.ClientDto;
import com.example.banking_authorization_server.dto.MessageDto;
import com.example.banking_authorization_server.entity.Client;
import com.example.banking_authorization_server.repository.ClientRepository;
import com.example.banking_authorization_server.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService, RegisteredClientRepository {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Client clientFromDto(ClientDto clientDto) {
        Client client = Client.builder()
                .clientId(clientDto.getClientId())
                .clientSecret(passwordEncoder.encode(clientDto.getClientSecret()))
                .authenticationMethods(clientDto.getAuthenticationMethods())
                .authorizationGrantTypes(clientDto.getAuthorizationGrantTypes())
                .redirectUris(clientDto.getRedirectUris())
                .scopes(clientDto.getScopes())
                .requireProofKey(clientDto.isRequireProofKey())
                .build();
        return client;
    }

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = clientRepository.findByClientId(id)
                .orElseThrow(() -> new RuntimeException("client not found"));
        return Client.toRegisteredClient(client);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("client not found"));
        return Client.toRegisteredClient(client);
    }

    @Override
    public MessageDto createClient(ClientDto clientDto){
        Client client = clientFromDto(clientDto);
        clientRepository.save(client);
        return new MessageDto("client " + client.getClientId() + " saved");
    }

}
