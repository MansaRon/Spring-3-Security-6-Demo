package za.co.security.Spring3.Security6.Demo.dtos;

import lombok.Data;
import za.co.security.Spring3.Security6.Demo.models.UserRole;

import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private Set<UserRole> roles;
}
