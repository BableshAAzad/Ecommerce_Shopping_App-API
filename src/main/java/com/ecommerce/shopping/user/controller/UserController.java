package com.ecommerce.shopping.user.controller;

import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.user.dto.*;
import com.ecommerce.shopping.user.service.UserService;
import com.ecommerce.shopping.utility.ResponseStructure;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    //------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/sellers/register")
    public ResponseEntity<ResponseStructure<UserResponse>> addSeller(
            @Valid @RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest, UserRole.SELLER);
    }

    //------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/customers/register")
    public ResponseEntity<ResponseStructure<UserResponse>> addCustomer(
            @Valid @RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest, UserRole.CUSTOMER);
    }


    @PostMapping("/users/otpVerification")
    public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(
          @Valid @RequestBody OtpVerificationRequest otpVerificationRequest) {
        return userService.verifyUserOtp(otpVerificationRequest);
    }

    //------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/users/resendOtp")
    public ResponseEntity<ResponseStructure<UserResponse>> resendOtp(
            @Valid @RequestBody UserRequest userRequest) {
        return userService.resendOtp(userRequest);
    }

    //------------------------------------------------------------------------------------------------------------------------
    @PutMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('SELLER')")
    public ResponseEntity<ResponseStructure<UserResponse>> updateUser(
            @Valid @RequestBody UserRequest userRequest,
            @Valid @PathVariable Long userId) {
        return userService.updateUser(userRequest, userId);
    }

    @PutMapping("/users/update/{email}")
    public ResponseEntity<ResponseStructure<UserResponse>> passwordResetByEmail(
            @Valid  @PathVariable String email) {
        return userService.passwordResetByEmail(email);
    }

    @PutMapping("/users/update")
    public ResponseEntity<ResponseStructure<UserResponse>> passwordResetByEmailVerification(
            @Valid  @RequestBody UserRequest userRequest,
            @Valid @RequestParam String secrete) {
        return userService.passwordResetByEmailVerification(userRequest, secrete);
    }

    //------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('SELLER')")
    public ResponseEntity<ResponseStructure<UserResponse>> findUser(
            @Valid @PathVariable Long userId) {
        return userService.findUser(userId);
    }

    //------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('SELLER')")
    public ResponseEntity<ResponseStructure<List<UserResponse>>> findUsers() {
        return userService.findUsers();
    }

    //------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<AuthResponse>> login(
            @Valid @RequestBody AuthRequest authRequest) {
        return userService.login(authRequest);
    }

//    @PostMapping("/login/oauth2/code/google")
//    public String loginGoogle(@RequestBody AuthRequest authRequest) {
//        System.out.println(authRequest);
//        return authRequest.toString();
//    }



    //------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/refreshLogin")
    @PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('SELLER')")
    public ResponseEntity<ResponseStructure<AuthResponse>> refreshLogin(
            @CookieValue(value = "rt", required = false) String refreshToken) {
        return userService.refreshLogin(refreshToken);
    }

    //------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('SELLER')")
    public ResponseEntity<LogoutResponse> logout(@CookieValue(value = "rt", required = false) String refreshToken,
                                                 @CookieValue(value = "at", required = false) String accessToken) {
        return userService.logout(refreshToken, accessToken);
    }

    //------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/logoutFromOtherDevices")
    @PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('SELLER')")
    public ResponseEntity<LogoutResponse> logoutFromOtherDevices(@CookieValue(value = "rt", required = false) String refreshToken,
                                                                 @CookieValue(value = "at", required = false) String accessToken) {
        return userService.logoutFromOtherDevices(refreshToken, accessToken);
    }

    //------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/logoutFromAllDevices")
    @PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('SELLER')")
    public ResponseEntity<LogoutResponse> logoutFromAllDevices(@CookieValue(value = "rt", required = false) String refreshToken,
                                                               @CookieValue(value = "at", required = false) String accessToken) {
        return userService.logoutFromAllDevices(refreshToken, accessToken);
    }

    //------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------

}
