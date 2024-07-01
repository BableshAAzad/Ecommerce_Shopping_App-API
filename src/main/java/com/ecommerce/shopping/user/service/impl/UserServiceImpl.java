package com.ecommerce.shopping.user.service.impl;

import com.ecommerce.shopping.customer.entity.Customer;
import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.exception.*;
import com.ecommerce.shopping.jwt.JwtService;
import com.ecommerce.shopping.mail.entity.MessageData;
import com.ecommerce.shopping.mail.service.MailService;
import com.ecommerce.shopping.seller.entity.Seller;
import com.ecommerce.shopping.user.dto.*;
import com.ecommerce.shopping.user.entity.AccessToken;
import com.ecommerce.shopping.user.entity.RefreshToken;
import com.ecommerce.shopping.user.entity.User;
import com.ecommerce.shopping.user.mapper.UserMapper;
import com.ecommerce.shopping.user.repositoty.AccessTokenRepository;
import com.ecommerce.shopping.user.repositoty.RefreshTokenRepository;
import com.ecommerce.shopping.user.repositoty.UserRepository;
import com.ecommerce.shopping.user.service.UserService;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Service
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
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${application.jwt.access_expiry_seconds}")
    private long accessExpirySeconds;

    @Value("${application.jwt.refresh_expiry_seconds}")
    private long refreshExpireSeconds;

    @Value("${application.cookie.domain}")
    private String domain;

    @Value("${application.cookie.same_site}")
    private String sameSite;

    @Value("${application.cookie.secure}")
    private boolean secure;


    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           Cache<String, User> userCache,
                           Cache<String, String> otpCache,
                           Random random,
                           MailService mailService,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           AccessTokenRepository accessTokenRepository,
                           RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userCache = userCache;
        this.otpCache = otpCache;
        this.random = random;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.accessTokenRepository = accessTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

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
    public ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                jwtService.createJwtToken(authRequest.getUsername(), 1000000L);
            } else
                throw new BadCredentialsException("Invalid Credentials");

            return userRepository.findByUsername(authRequest.getUsername()).map(existUser -> {
                HttpHeaders httpHeaders = new HttpHeaders();
                grantAccessToken(httpHeaders, existUser);
                grantRefreshToken(httpHeaders, existUser);

                return ResponseEntity.status(HttpStatus.OK)
                        .headers(httpHeaders)
                        .body(new ResponseStructure<AuthResponse>()
                                .setStatus(HttpStatus.OK.value())
                                .setMessage("User Verified")
                                .setData(AuthResponse.builder()
                                        .userId(existUser.getUserId())
                                        .username(existUser.getUsername())
                                        .accessExpiration(accessExpirySeconds)
                                        .refreshExpiration(refreshExpireSeconds)
                                        .build()));
            }).orElseThrow(() -> new UserNotExistException("Username : " + authRequest.getUsername() + ", is not found"));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid Credentials", e);
        }
    }

    //------------------------------------------------------------------------------------------------------------------------
    public void grantAccessToken(HttpHeaders httpHeaders, User user) {
        String token = jwtService.createJwtToken(user.getUsername(), accessExpirySeconds);

        AccessToken accessToken = AccessToken.builder()
                .accessToken(token)
                .expiration(LocalDateTime.now().plusSeconds(accessExpirySeconds))
                .user(user)
                .build();
        accessTokenRepository.save(accessToken);

        httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("ar", token, accessExpirySeconds));
    }

    //------------------------------------------------------------------------------------------------------------------------
    public void grantRefreshToken(HttpHeaders httpHeaders, User user) {

        String token = jwtService.createJwtToken(user.getUsername(), refreshExpireSeconds);

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .expiration(LocalDateTime.now().plusSeconds(refreshExpireSeconds))
                .user(user)
                .build();
        refreshTokenRepository.save(refreshToken);

        httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("rt", token, refreshExpireSeconds));
    }

    //------------------------------------------------------------------------------------------------------------------------
    private String generateCookie(String name, String tokenValue, long maxAge) {
        return ResponseCookie.from(name, tokenValue)
                .domain(domain)
                .path("/")
                .maxAge(maxAge)
                .sameSite(sameSite)
                .httpOnly(true)
                .secure(secure)
                .build()
                .toString();
    }
    //------------------------------------------------------------------------------------------------------------------------
}
