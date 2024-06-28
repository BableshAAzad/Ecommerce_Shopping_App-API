package com.ecommerce.shopping.user.service.impl;

import com.ecommerce.shopping.customer.entity.Customer;
import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.exception.*;
import com.ecommerce.shopping.jwt.JwtService;
import com.ecommerce.shopping.mail.entity.MessageData;
import com.ecommerce.shopping.mail.service.MailService;
import com.ecommerce.shopping.seller.entity.Seller;
import com.ecommerce.shopping.user.dto.AuthRequest;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final Cache<String, User> userCache;

    private final Cache<String, String> otpCache;

    private final Random random;

    private final MailService mailService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    //------------------------------------------------------------------------------------------------------------------------

    public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest, UserRole userRole) {
        boolean emailExist = userRepository.existsByEmail(userRequest.getEmail());
        if (emailExist)
            throw new UserAlreadyExistException("Email : " + userRequest.getEmail() + ", is already exist");
        else {
            User user = null;
            switch (userRole) {
                case UserRole.SELLER -> user = new Seller();
                case UserRole.CUSTOMER -> user = new Customer();
            }
            if (user != null) {
                user = userMapper.mapUserRequestToUser(userRequest, user);
                user.setUserRole(userRole);
                userCache.put(userRequest.getEmail(), user);
                int otp = random.nextInt(100000, 999999);
                otpCache.put(userRequest.getEmail(), otp + "");

//                Send otp in mail
                mailSend(user.getEmail(), "OTP verification for EcommerceShoppingApp", "<h3>Welcome to Ecommerce Shopping Applicationa</h3></br><h4>Otp : " + otp + "</h4>");

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseStructure<UserResponse>()
                        .setStatus(HttpStatus.ACCEPTED.value())
                        .setMessage("Otp sended")
                        .setData(userMapper.mapUserToUserResponse(user)));
            } else throw new UserAlreadyExistException("Bad Request");
        }
    }

    //    Logic for mail generation
    private void mailSend(String email, String subject, String text) {
        MessageData messageData = new MessageData();
        messageData.setTo(email);
        messageData.setSubject(subject);
        messageData.setText(text);
        messageData.setSendDate(new Date());
        try {
            mailService.sendMail(messageData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> verifyUserOtp(OtpVerificationRequest otpVerificationRequest) {
        User user = userCache.getIfPresent(otpVerificationRequest.getEmail());
        String otp = otpCache.getIfPresent(otpVerificationRequest.getEmail());
        if (user == null && otp == null) {
            throw new IllegalOperationException("Please Enter correct information");
        } else if (otp == null && user.getEmail().equals(otpVerificationRequest.getEmail())) {
//            if user otp will be expired
            throw new OtpExpiredException("Otp is expired");
        } else if (!otp.equals(otpVerificationRequest.getOtp())) {
//            oto mismatch with existing otp   or   invalid otp
            throw new InvalidOtpException("Invalid otp");
        } else if (otp.equals(otpVerificationRequest.getOtp()) && user != null) {
//            If user otp and cache otp
//           Create Dynamic username
            String userGen = usernameGenerate(user.getEmail());
            user.setUsername(userGen);
            user.setEmailVerified(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);

//            Send mail to user for confirmation
            mailSend(user.getEmail(), "Email Verification done", "<h3>Your account is created in EcommerceShoppingApp</h3></br><h4>Your username is : " + userGen + "</h4>");

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<UserResponse>()
                    .setStatus(HttpStatus.CREATED.value())
                    .setMessage(user.getUserRole() + " Created")
                    .setData(userMapper.mapUserToUserResponse(user)));
        } else {
            throw new OtpExpiredException("Otp is expired");
        }
    }

    private String usernameGenerate(String email) {
        String[] str = email.split("@");
        String username = str[0];
        int temp = 0;
        while (true) {
            if (userRepository.existsByUsername(username)) {
                username += temp;
                temp++;
                continue;
            } else
                break;
        }
        if (temp != 0) {
            return username;
        } else {
            return str[0];
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
    @Override
    public String login(AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authenticate.isAuthenticated())
                return jwtService.createJwtToken(authRequest.getUsername(), 1000000L);
            else
                throw new BadCredentialsException("Invalid Credentials");
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid Credentials", e);
        }
    }
    //------------------------------------------------------------------------------------------------------------------------
}
