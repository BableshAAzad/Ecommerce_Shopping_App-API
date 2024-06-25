package com.ecommerce.shopping.user.dto;

import lombok.Getter;

@Getter
public class OtpVerificationRequest {
    private String email;
    private String otp;
}
