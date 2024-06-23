package com.ecommerce.shopping.user.dto;


import com.ecommerce.shopping.enums.UserRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    private UserRole userRole;
}
