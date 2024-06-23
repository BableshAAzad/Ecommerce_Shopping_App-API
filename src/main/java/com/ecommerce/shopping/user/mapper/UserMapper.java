package com.ecommerce.shopping.user.mapper;

import com.ecommerce.shopping.user.dto.UserRequest;
import com.ecommerce.shopping.user.dto.UserResponse;
import com.ecommerce.shopping.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapUserRequestToUser(UserRequest userRequest, User user){
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        return  user;
    }

    public UserResponse mapUserToUserResponse(User user){
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .build();
    }
}
