package com.example.banking_authorization_server.repository;

import com.example.banking_authorization_server.entity.RoleName;
import com.example.banking_authorization_server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRole(RoleName roleName);
}
