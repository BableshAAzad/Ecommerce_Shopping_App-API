package com.ecommerce.shopping.user.service.impl;

import com.ecommerce.shopping.config.UtilityBeanConfig;
import com.ecommerce.shopping.customer.entity.Customer;
import com.ecommerce.shopping.customer.repository.CustomerRepository;
import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.exception.UserAlreadyExistException;
import com.ecommerce.shopping.exception.UserNotExistException;
import com.ecommerce.shopping.seller.entity.Seller;
import com.ecommerce.shopping.seller.repository.SellerRepository;
import com.ecommerce.shopping.user.dto.OtpVerificationRequest;
import com.ecommerce.shopping.user.dto.UserRequest;
import com.ecommerce.shopping.user.dto.UserResponse;
import com.ecommerce.shopping.user.entity.User;
import com.ecommerce.shopping.user.mapper.UserMapper;
import com.ecommerce.shopping.user.repositoty.UserRepository;
import com.ecommerce.shopping.user.service.UserService;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.google.common.cache.Cache;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final SellerRepository sellerRepository;

    private final CustomerRepository customerRepository;

    private final Cache<String, User> userCache;

    private final Cache<String, String> otpCache;

    private final Random random;

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> addUser(UserRequest userRequest, UserRole userRole) {
        boolean emailExist = userRepository.existsByEmail(userRequest.getEmail());
        if (emailExist)
            throw new UserAlreadyExistException("Email : " + userRequest.getEmail() + ", is already exist");
        else {
            if (userRole.equals(UserRole.CUSTOMER)) {
                Customer customer = (Customer) userMapper.mapUserRequestToUser(userRequest, new Customer());
                customer.setUserRole(userRole);

//                TODO logic for username creation

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

    public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest, UserRole userRole) {
        User user = null;
        switch (userRole) {
            case UserRole.SELLER -> user = new Seller();
            case UserRole.CUSTOMER -> user = new Customer();
        }
        if (user != null) {
            user = userMapper.mapUserRequestToUser(userRequest, user);
            userCache.put(userRequest.getEmail(), user);
            int otp = random.nextInt(100000, 999999);
            System.out.println(otp);
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
            otpCache.put(userRequest.getEmail(), otp + "");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseStructure<UserResponse>()
                    .setStatus(HttpStatus.ACCEPTED.value())
                    .setMessage("Verify otp")
                    .setData(userMapper.mapUserToUserResponse(user)));
        } else throw new UserAlreadyExistException("Bad Request");
    }

    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(OtpVerificationRequest otpVerificationRequest) {
        User user = userCache.getIfPresent(otpVerificationRequest.getEmail());
        String otp = otpCache.getIfPresent(otpVerificationRequest.getEmail());
        System.out.println(user.getEmail());
        System.out.println("-------------------------------------------------------------------");
        System.out.println(otp);
        return null;
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
                .map(userMapper::mapUserToUserResponse)
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
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<UserResponse>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("User Updated")
                    .setData(userMapper.mapUserToUserResponse(user)));
        }).orElseThrow(() -> new UserNotExistException("UserId : " + userId + ", is not exist"));
    }
//------------------------------------------------------------------------------------------------------------------------
}
