package com.ecommerce.shopping.user.dto;

import com.ecommerce.shopping.enums.UserRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private Long userId;
    private String username;
    private UserRole userRole;
    private long accessExpiration;
    private long refreshExpiration;

}
