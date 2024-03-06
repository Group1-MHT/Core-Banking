package com.example.banking_manager_user.serviceImpl;

import com.example.banking_manager_user.entity.Role;
import com.example.banking_manager_user.exception.NotFoundException;
import com.example.banking_manager_user.repository.RoleRepository;
import com.example.banking_manager_user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Integer roleId, Role updatedRole) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        existingRole.setName(updatedRole.getName());

        return roleRepository.save(existingRole);
    }

    public void deleteRole(Integer roleId) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        roleRepository.delete(existingRole);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
