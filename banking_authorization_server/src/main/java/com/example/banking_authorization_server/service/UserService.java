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

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;



    public MessageDto createUser(UserDto userDto){
        User user = User.builder()
                .username(userDto.username())
                .password(passwordEncoder.encode(userDto.password()))
                .build();
        Set<Role> roles = new HashSet<>();
        userDto.roles().forEach(r -> {
            Role role = roleRepository.findByRole(RoleName.valueOf(r))
                    .orElseThrow(() -> new RuntimeException("role not found"));
            roles.add(role);
        });
        user.setRoles(roles);
        userRepository.save(user);
        return new MessageDto("user " + user.getUsername() + " saved");
    }
}
