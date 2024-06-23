package com.ecommerce.shopping.user.service.impl;

import com.ecommerce.shopping.customer.entity.Customer;
import com.ecommerce.shopping.customer.repository.CustomerRepository;
import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.exception.UserAlreadyExistException;
import com.ecommerce.shopping.exception.UserNotExistException;
import com.ecommerce.shopping.seller.entity.Seller;
import com.ecommerce.shopping.seller.repository.SellerRepository;
import com.ecommerce.shopping.user.dto.UserRequest;
import com.ecommerce.shopping.user.dto.UserResponse;
import com.ecommerce.shopping.user.mapper.UserMapper;
import com.ecommerce.shopping.user.repositoty.UserRepository;
import com.ecommerce.shopping.user.service.UserService;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private CustomerRepository customerRepository;

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> addUser(UserRequest userRequest, UserRole userRole) {
        boolean userExist = userRepository.existsByEmail(userRequest.getEmail());
        if (userExist) throw new UserAlreadyExistException("Email : " + userRequest.getEmail() + ", is already exist");
        else {
            if (userRole.equals(UserRole.CUSTOMER)) {
                Customer customer = (Customer) userMapper.mapUserRequestToUser(userRequest, new Customer());
                customer.setUserRole(userRole);
                customer = customerRepository.save(customer);

                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<UserResponse>()
                        .setStatus(HttpStatus.CREATED.value())
                        .setMessage("Customer Created")
                        .setData(userMapper.mapUserToUserResponse(customer)));
            } else {
                Seller seller = (Seller) userMapper.mapUserRequestToUser(userRequest, new Seller());
                seller.setUserRole(userRole);
                seller = sellerRepository.save(seller);

                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<UserResponse>()
                        .setStatus(HttpStatus.CREATED.value())
                        .setMessage("Seller Created")
                        .setData(userMapper.mapUserToUserResponse(seller)));
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> findUser(Long userId) {
        return userRepository.findById(userId).map(user -> {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<UserResponse>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("User Founded")
                    .setData(userMapper.mapUserToUserResponse(user)));
        }).orElseThrow(() -> new UserNotExistException("UserId : " + userId + ", is not exist"));
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<List<UserResponse>>> findUsers() {
        List<UserResponse> userResponseList = userRepository.findAll()
                .stream()
                .map(user -> userMapper.mapUserToUserResponse(user))
                .toList();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<UserResponse>>()
                .setMessage("Users are Founded")
                .setData(userResponseList));
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> updateUser(UserRequest userRequest, Long userId) {
        return userRepository.findById(userId).map(user -> {
            user = userMapper.mapUserRequestToUser(userRequest, user);
            user = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<UserResponse>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("User Updated")
                    .setData(userMapper.mapUserToUserResponse(user)));
        }).orElseThrow(() -> new UserNotExistException("UserId : " + userId + ", is not exist"));
    }
//------------------------------------------------------------------------------------------------------------------------
}
