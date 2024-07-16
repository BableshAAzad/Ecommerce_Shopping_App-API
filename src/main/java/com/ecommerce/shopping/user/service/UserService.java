package com.ecommerce.shopping.user.service;

import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.user.dto.*;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<ResponseStructure<UserResponse>> updateUser(UserRequest userRequest, Long userId);

    ResponseEntity<ResponseStructure<UserResponse>> findUser(Long userId);

    ResponseEntity<ResponseStructure<List<UserResponse>>> findUsers();

    ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest, UserRole userRole);

    ResponseEntity<ResponseStructure<UserResponse>> verifyUserOtp(OtpVerificationRequest otpVerificationRequest);

    ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest);

    ResponseEntity<ResponseStructure<AuthResponse>> refreshLogin(String refreshToken);

    ResponseEntity<LogoutResponse> logout(String refreshToken, String accessToken);

    ResponseEntity<LogoutResponse> logoutFromOtherDevices(String refreshToken, String accessToken);

    ResponseEntity<LogoutResponse> logoutFromAllDevices(String refreshToken, String accessToken);

    ResponseEntity<ResponseStructure<UserResponse>> resendOtp(UserRequest userRequest);
}
