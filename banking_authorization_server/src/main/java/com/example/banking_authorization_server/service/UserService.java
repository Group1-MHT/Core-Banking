package com.example.banking_authorization_server.service;

import com.example.banking_authorization_server.dto.MessageDto;
import com.example.banking_authorization_server.dto.UserDto;
import com.example.banking_authorization_server.entity.RoleName;
import com.example.banking_authorization_server.entity.Role;
import com.example.banking_authorization_server.entity.User;
import com.example.banking_authorization_server.repository.RoleRepository;
import com.example.banking_authorization_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
public interface UserService {
    MessageDto registerUser(UserDto userDto);
}
