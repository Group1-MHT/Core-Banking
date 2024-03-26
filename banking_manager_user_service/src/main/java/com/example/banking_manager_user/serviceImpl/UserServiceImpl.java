package com.example.banking_manager_user.serviceImpl;

import com.example.banking_manager_user.dto.UserDto;
import com.example.banking_manager_user.model.Role;
import com.example.banking_manager_user.model.User;
import com.example.banking_manager_user.exceptions.ErrorCode;
import com.example.banking_manager_user.exceptions.exception.AppException;
import com.example.banking_manager_user.repository.RoleRepository;
import com.example.banking_manager_user.repository.UserRepository;
import com.example.banking_manager_user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    public User createUser(UserDto userDto) {
        User user = User.builder()
                .createdAt(LocalDateTime.now())
                .email(userDto.getEmail())
                .fullName(userDto.getFullName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .build();
        List<String> roleNames = new ArrayList<>();
        Set<Role> roles = new HashSet<>();

        for (String roleName : roleNames){
            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(roleName);
                        return roleRepository.save(newRole);
                    });
            roles.add(role);
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User updateUser(Integer userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    public List<Role> getUserRoles(Integer userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return (List<Role>) existingUser.getRoles();
    }

    public void grantRole(Integer userId, Integer roleId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        existingUser.getRoles().add(role);
        userRepository.save(existingUser);
    }

    public void revokeRole(Integer userId, Integer roleId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        existingUser.getRoles().remove(role);
        userRepository.save(existingUser);
    }

    public User getById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}
