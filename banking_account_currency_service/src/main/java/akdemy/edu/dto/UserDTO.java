package akdemy.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private LocalDateTime createdAt;
    private Set<RoleDTO> roles = new HashSet<>();
}
