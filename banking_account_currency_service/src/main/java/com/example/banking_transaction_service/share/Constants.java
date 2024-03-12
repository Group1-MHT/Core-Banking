package com.example.banking_transaction_service.share;

import com.example.banking_transaction_service.dto.RoleDTO;

public class Constants {
    public static final RoleDTO ROLE_ADMIN = new RoleDTO(1L, "admin");
    public static final RoleDTO ROLE_STAFF = new RoleDTO(2L, "staff");
    public static final RoleDTO ROLE_USER = new RoleDTO(3L, "user");
}
