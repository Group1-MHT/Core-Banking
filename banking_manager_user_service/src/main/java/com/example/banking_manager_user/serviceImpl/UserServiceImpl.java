package com.example.banking_manager_user.serviceImpl;

import com.example.banking_manager_user.dto.UserDto;
import com.example.banking_manager_user.entity.Role;
import com.example.banking_manager_user.entity.User;
import com.example.banking_manager_user.exception.NotFoundException;
import com.example.banking_manager_user.repository.RoleRepository;
import com.example.banking_manager_user.repository.UserRepository;
import com.example.banking_manager_user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User createUser(UserDto userDto, List<String> roleNames) {

        return userRepository.save(getUserFromRequest(userDto,roleNames));
    }

    public User updateUser(Integer userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFullName(updatedUser.getFullName());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Integer userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(existingUser);
    }

    public List<Role> getUserRoles(Integer userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return (List<Role>) existingUser.getRoles();
    }

    public void grantRole(Integer userId, Integer roleId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        existingUser.getRoles().add(role);
        userRepository.save(existingUser);
    }

    public void revokeRole(Integer userId, Integer roleId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        existingUser.getRoles().remove(role);
        userRepository.save(existingUser);
    }

    public User getById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    public User getUserFromRequest(UserDto userDto, List<String> roleNames){
        User user = new User();

        user.setCreatedAt(LocalDateTime.now());

        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());

        Set<Role> roles = new HashSet<>();

        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(roleName);
                        return roleRepository.save(newRole);
                    });
            roles.add(role);
        }
        user.setRoles(roles);
        return user;
    }
}
