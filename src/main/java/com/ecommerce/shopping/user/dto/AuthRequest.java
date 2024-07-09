package com.ecommerce.shopping.user.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthRequest {

    private String username;
    private String password;

}
