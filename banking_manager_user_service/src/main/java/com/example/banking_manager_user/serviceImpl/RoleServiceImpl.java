package com.example.banking_manager_user.serviceImpl;

import com.example.banking_manager_user.dto.RoleDto;
import com.example.banking_manager_user.exceptions.ErrorCode;
import com.example.banking_manager_user.exceptions.exception.AppException;
import com.example.banking_manager_user.model.Role;
import com.example.banking_manager_user.repository.RoleRepository;
import com.example.banking_manager_user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(RoleDto roleDto) {
        return roleRepository.save(getRoleFromDto(roleDto));
    }

    public Role updateRole(Integer roleId, Role updatedRole) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        existingRole.setName(updatedRole.getName());

        return roleRepository.save(existingRole);
    }

    public void deleteRole(Integer roleId) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        roleRepository.delete(existingRole);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    public Role getRoleFromDto(RoleDto roleDto){
        Role role = new Role();
        role.setName(roleDto.getName());
        return role;
    }
}
