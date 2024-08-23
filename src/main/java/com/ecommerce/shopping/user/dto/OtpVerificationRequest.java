package com.ecommerce.shopping.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OtpVerificationRequest {
    @NotBlank(message = "Email can not be blank")
    @NotNull(message = "Email can not be null")
    private String email;
    private String otp;
}
