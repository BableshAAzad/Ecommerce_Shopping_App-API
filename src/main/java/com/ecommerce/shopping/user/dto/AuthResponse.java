package com.ecommerce.shopping.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private Long userId;
    private String username;
    private long accessExpiration;
    private long refreshExpiration;

}
