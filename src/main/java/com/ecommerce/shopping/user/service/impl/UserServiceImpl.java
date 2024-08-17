package com.ecommerce.shopping.user.service.impl;

import com.ecommerce.shopping.customer.entity.Customer;
import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.exception.*;
import com.ecommerce.shopping.jwt.JwtService;
import com.ecommerce.shopping.mail.dto.MessageData;
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
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

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

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final Cache<String, User> userCache;
    private final Cache<String, String> otpCache;
    private final Cache<String, String> secreteKey;
    private final Random random;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           Cache<String, User> userCache,
                           Cache<String, String> otpCache,
                           Cache<String, String> secreteKey,
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
        this.secreteKey = secreteKey;
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

                String otpExpired = otpExpirationTimeCalculate(5);
//                Send otp in mail
                mailSend(user.getEmail(), "OTP verification for EcommerceShoppingApp", "<h3>Welcome to Ecommerce Shopping Applicationa</h3></br><h4>Otp : " + otp + "</h4></br><p>" + otpExpired + "</p>");

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseStructure<UserResponse>()
                        .setStatus(HttpStatus.ACCEPTED.value())
                        .setMessage("Otp sended")
                        .setData(userMapper.mapUserToUserResponse(user)));
            } else throw new UserAlreadyExistException("Bad Request");
        }
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> resendOtp(UserRequest userRequest) {
        User user = userCache.getIfPresent(userRequest.getEmail());
        System.out.println(user);
        if (user != null) {
            int otp = random.nextInt(100000, 999999);
            otpCache.put(userRequest.getEmail(), otp + "");

            String otpExpired = otpExpirationTimeCalculate(5);
//          Re-Send otp in mail
            mailSend(user.getEmail(), "OTP verification for EcommerceShoppingApp", "<h3>Welcome to Ecommerce Shopping Applicationa</h3></br><h4>Regenerated Otp : " + otp + "</h4></br><p>" + otpExpired + "</p>");

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseStructure<UserResponse>()
                    .setStatus(HttpStatus.ACCEPTED.value())
                    .setMessage("Otp re-sended")
                    .setData(userMapper.mapUserToUserResponse(user)));
        } else throw new UserNotExistException("Email : " + userRequest.getEmail() + ", is not exist");
    }

    //------------------------------------------------------------------------------------------------------------------------
    //    Logic for mail generation
    @Override
    public void mailSend(String email, String subject, String text) {
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
    private String otpExpirationTimeCalculate(int expired) {
        // Get the current time
        LocalDateTime currentTime = LocalDateTime.now();

        // Add 5 minutes to the current time
        LocalDateTime timeAfterFiveMinutes = currentTime.plusMinutes(expired);

        // Convert LocalDateTime to Date
        ZonedDateTime zonedDateTime = timeAfterFiveMinutes.atZone(ZoneId.systemDefault());
        Date dateAfterFiveMinutes = Date.from(zonedDateTime.toInstant());

        return "Otp will be expired after 5 minutes: " + dateAfterFiveMinutes;
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
            if (user.getUsername() == null) {
                String userGen = usernameGenerate(user.getEmail());
                user.setUsername(userGen);
                user.setEmailVerified(true);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user = userRepository.save(user);
                //            Send mail to user for confirmation
                mailSend(user.getEmail(), "Email Verification done", "<h3>Your account is created in EcommerceShoppingApp</h3></br><h4>Your username is : " + userGen + " and UserRole is : " + user.getUserRole() + "</h4>");
            } else if (user.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user = userRepository.save(user);
                mailSend(user.getEmail(), "Profile successfully updated", "<h3>Your account is updated in EcommerceShoppingApp</h3></br><h4>Your username is : " + user.getUsername() + " and UserRole is : " + user.getUserRole() + "</h4>");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<UserResponse>()
                        .setStatus(HttpStatus.OK.value())
                        .setMessage("User verified")
                        .setData(userMapper.mapUserToUserResponse(user)));
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<UserResponse>()
                    .setStatus(HttpStatus.CREATED.value())
                    .setMessage(user.getUserRole() + " Created or Updated")
                    .setData(userMapper.mapUserToUserResponse(user)));
        } else {
            throw new OtpExpiredException("Otp is expired");
        }
    }
    //------------------------------------------------------------------------------------------------------------------------

    @Override
    public String usernameGenerate(String email) {
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
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<UserResponse>()
                    .setStatus(HttpStatus.OK.value())
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
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<List<UserResponse>>()
                .setMessage("Users are Founded")
                .setData(userResponseList));
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> updateUser(UserRequest userRequest, Long userId) {
        return userRepository.findById(userId).map(user -> {
            if (user.getEmail().equals(userRequest.getEmail())) {
                user = userMapper.mapUserRequestToUser(userRequest, user);
                userCache.put(userRequest.getEmail(), user);
                int otp = random.nextInt(100000, 999999);
                otpCache.put(userRequest.getEmail(), otp + "");

                String otpExpired = otpExpirationTimeCalculate(5);
//                Send otp in mail
                mailSend(user.getEmail(), "OTP verification for EcommerceShoppingApp", "<h3>Welcome to Ecommerce Shopping Applicationa</h3></br><h4>Otp : " + otp + "</h4></br><p>" + otpExpired + "</p>");
            } else {
                boolean existEmail = userRepository.existsByEmail(userRequest.getEmail());
                if (existEmail)
                    throw new UserAlreadyExistException("Email : " + userRequest.getEmail() + ", is already exist");
                else {
                    user = userMapper.mapUserRequestToUser(userRequest, user);
                    userCache.put(userRequest.getEmail(), user);
                    int otp = random.nextInt(100000, 999999);
                    otpCache.put(userRequest.getEmail(), otp + "");

                    String otpExpired = otpExpirationTimeCalculate(5);
//                Send otp in mail
                    mailSend(user.getEmail(), "OTP verification for EcommerceShoppingApp", "<h3>Welcome to Ecommerce Shopping Applicationa</h3></br><h4>Otp : " + otp + "</h4></br><p>" + otpExpired + "</p>");
                }
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("rt", null, 0));
            httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("at", null, 0));
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(httpHeaders)
                    .body(new ResponseStructure<UserResponse>()
                            .setStatus(HttpStatus.OK.value())
                            .setMessage("Otp sended")
                            .setData(userMapper.mapUserToUserResponse(user)));
        }).orElseThrow(() -> new UserNotExistException("UserId : " + userId + ", is not exist"));
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> passwordResetByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistException("Email Id : " + email + ", is not exist"));
        user.setPassword(null);
        userCache.put(user.getEmail(), user);
        int otp = random.nextInt(100000, 999999);
        otpCache.put(user.getEmail(), otp + "");
        int key = random.nextInt(100000, 999999);
        secreteKey.put(user.getEmail(), key + "");

        String otpExpired = otpExpirationTimeCalculate(5);
//                Send otp in mail
        mailSend(user.getEmail(), "OTP verification for EcommerceShoppingApp",
                "<h3>Welcome to Ecommerce Shopping Application</h3></br><h4>Otp : " + otp + "</h4></br><p>" + otpExpired + "</p></br><h4>Secret Key for password reset : " + key + "</h4>");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseStructure<UserResponse>()
                        .setStatus(HttpStatus.OK.value())
                        .setMessage("Otp sended")
                        .setData(userMapper.mapUserToUserResponse(user)));
    }
    //------------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> passwordResetByEmailVerification(UserRequest userRequest, String secrete) {
        User cacheUser = userCache.getIfPresent(userRequest.getEmail());
        User existUser = userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new UserNotExistException("Email Id : " + userRequest.getEmail() + ", is not exist"));
        String key = secreteKey.getIfPresent(userRequest.getEmail());
        if (cacheUser != null && key != null && key.equals(secrete)) {
            existUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            existUser = userRepository.save(existUser);
            mailSend(existUser.getEmail(), "Password reset done at EcommerceShoppingApp",
                    "<h3>Welcome to Ecommerce Shopping Applicationa</h3></br><p>Your password reset successfully done</p></br><h4>Your Username is : " + existUser.getUsername() + "</h4>");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseStructure<UserResponse>()
                            .setStatus(HttpStatus.OK.value())
                            .setMessage("Password Reset done")
                            .setData(userMapper.mapUserToUserResponse(existUser)));
        } else {
            throw new IllegalOperationException("Session expired please try again...!!!");
        }
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
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
                                            .userRole(existUser.getUserRole())
                                            .accessExpiration(accessExpirySeconds)
                                            .refreshExpiration(refreshExpireSeconds)
                                            .build()));
                }).orElseThrow(() -> new UserNotExistException("Username : " + authRequest.getUsername() + ", is not found"));
            } else
                throw new BadCredentialsException("Invalid Credentials");
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid Credentials", e);
        }
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public void grantAccessToken(HttpHeaders httpHeaders, User user) {
        String token = jwtService.createJwtToken(user.getUsername(), user.getUserRole(), (accessExpirySeconds * 1000)); // 1 hour in ms

        AccessToken accessToken = AccessToken.builder()
                .accessToken(token)
                .expiration(LocalDateTime.now().plusSeconds(accessExpirySeconds))
                .user(user)
                .build();
        accessTokenRepository.save(accessToken);

        httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("at", token, accessExpirySeconds));
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public void grantRefreshToken(HttpHeaders httpHeaders, User user) {

        String token = jwtService.createJwtToken(user.getUsername(), user.getUserRole(), (refreshExpireSeconds * 1000));

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .expiration(LocalDateTime.now().plusSeconds(refreshExpireSeconds))
                .user(user)
                .build();
        refreshTokenRepository.save(refreshToken);

        httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("rt", token, refreshExpireSeconds));
    }

    //------------------------------------------------------------------------------------------------------------------------
    public String generateCookie(String name, String tokenValue, long maxAge) {
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
    @Override
    public ResponseEntity<ResponseStructure<AuthResponse>> refreshLogin(String refreshToken) {
        Date expiryDate = jwtService.extractExpirationDate(refreshToken);
        if (expiryDate.getTime() < new Date().getTime()) {
            throw new TokenExpiredException("Refresh token was expired, Please make a new SignIn request");
        } else {
            String username = jwtService.extractUserName(refreshToken);
//            UserRole userRole = jwtService.extractUserRole(refreshToken);
            User user = userRepository.findByUsername(username).get();

            HttpHeaders httpHeaders = new HttpHeaders();
            grantAccessToken(httpHeaders, user);

            return ResponseEntity.status(HttpStatus.OK)
                    .headers(httpHeaders)
                    .body(new ResponseStructure<AuthResponse>()
                            .setStatus(HttpStatus.OK.value())
                            .setMessage("Access Toke renewed")
                            .setData(AuthResponse.builder()
                                    .userId(user.getUserId())
                                    .username(user.getUsername())
                                    .userRole(user.getUserRole())
                                    .accessExpiration(accessExpirySeconds)
                                    .refreshExpiration((expiryDate.getTime() - new Date().getTime()) / 1000)
                                    .build()));
        }
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<LogoutResponse> logout(String refreshToken, String accessToken) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);
        Optional<AccessToken> optionalAccessToken = accessTokenRepository.findByAccessToken(accessToken);
        RefreshToken existRefreshToken = optionalRefreshToken.get();
        AccessToken existAccessToken = optionalAccessToken.get();

        existRefreshToken.setBlocked(true);
        existAccessToken.setBlocked(true);
        refreshTokenRepository.save(existRefreshToken);
        accessTokenRepository.save(existAccessToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("rt", null, 0));
        httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("at", null, 0));

        return ResponseEntity.status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(LogoutResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("User logout done")
                        .build());
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<LogoutResponse> logoutFromOtherDevices(String refreshToken, String accessToken) {
        String username = jwtService.extractUserName(refreshToken);
        User user = userRepository.findByUsername(username).get();

        List<RefreshToken> listRT = refreshTokenRepository.findByUserAndIsBlockedAndRefreshTokenNot(user, false, refreshToken);
        List<AccessToken> listAT = accessTokenRepository.findByUserAndIsBlockedAndAccessTokenNot(user, false, accessToken);
        listRT.forEach(rt -> {
            rt.setBlocked(true);
            refreshTokenRepository.save(rt);
        });
        listAT.forEach(at -> {
            at.setBlocked(true);
            accessTokenRepository.save(at);
        });
        return ResponseEntity.status(HttpStatus.OK).body(LogoutResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Other Devices Logout done")
                .build());
    }

    //------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<LogoutResponse> logoutFromAllDevices(String refreshToken, String accessToken) {
        String username = jwtService.extractUserName(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotExistException("Username : " + ", is not exist"));

        List<RefreshToken> listRT = refreshTokenRepository.findByUserAndIsBlocked(user, false);
        List<AccessToken> listAT = accessTokenRepository.findByUserAndIsBlocked(user, false);
        listRT.forEach(rt -> {
            rt.setBlocked(true);
            refreshTokenRepository.save(rt);
        });
        listAT.forEach(at -> {
            at.setBlocked(true);
            accessTokenRepository.save(at);
        });

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("rt", null, 0));
        httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("at", null, 0));

        return ResponseEntity.status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(LogoutResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Logout successfully done from all devices")
                        .build());
    }

    //------------------------------------------------------------------------------------------------------------------------
}
