package com.ecommerce.shopping.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthRequest {

    @NotBlank(message = "Username can not be blank")
    @NotNull(message = "Username can not be null")
    private String username;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password must"
            + " contain at least one letter, one number, one special character")
    @NotBlank(message = "Password can not be blank")
    private String password;

}
