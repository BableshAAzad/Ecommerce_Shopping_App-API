package com.ecommerce.shopping.user.controller;

import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.user.dto.OtpVerificationRequest;
import com.ecommerce.shopping.user.dto.UserRequest;
import com.ecommerce.shopping.user.dto.UserResponse;
import com.ecommerce.shopping.user.service.UserService;
import com.ecommerce.shopping.utility.ResponseStructure;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    //------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/sellers/register")
    public ResponseEntity<ResponseStructure<UserResponse>> addSeller(@Valid @RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest, UserRole.SELLER);
    }

    //------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/customers/register")
    public ResponseEntity<ResponseStructure<UserResponse>> addCustomer(@Valid @RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest, UserRole.CUSTOMER);
    }


    @PostMapping("/users/otpVerification")
    public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(
            @RequestBody OtpVerificationRequest otpVerificationRequest){
        return userService.verifyUserOtp(otpVerificationRequest);
    }
    //------------------------------------------------------------------------------------------------------------------------
     @PutMapping("/users/{userId}")
     public ResponseEntity<ResponseStructure<UserResponse>> updateUser(
            @Valid @RequestBody UserRequest userRequest,
            @Valid @PathVariable Long userId){
        return userService.updateUser(userRequest, userId);
     }
    //------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseStructure<UserResponse>> findUser(@Valid @PathVariable Long userId) {
        return userService.findUser(userId);
    }

    //------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/users")
    public ResponseEntity<ResponseStructure<List<UserResponse>>> findUsers() {
        return userService.findUsers();
    }

//------------------------------------------------------------------------------------------------------------------------

//------------------------------------------------------------------------------------------------------------------------

}
