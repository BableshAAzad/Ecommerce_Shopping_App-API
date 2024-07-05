package com.ecommerce.shopping.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoutResponse {
    private int status;
    private String message;
}
