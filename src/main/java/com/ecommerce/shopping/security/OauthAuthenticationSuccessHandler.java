package com.ecommerce.shopping.security;

import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.user.dto.AuthResponse;
import com.ecommerce.shopping.user.entity.User;
import com.ecommerce.shopping.user.repositoty.UserRepository;
import com.ecommerce.shopping.user.service.UserService;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OauthAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${application.jwt.access_expiry_seconds}")
    private long accessExpirySeconds;

    @Value("${application.jwt.refresh_expiry_seconds}")
    private long refreshExpireSeconds;

    private final UserRepository userRepository;
    private final UserService userService;

    public OauthAuthenticationSuccessHandler(UserRepository userRepository,
                                            @Lazy UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        User user = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            String username = userService.usernameGenerate(email);
            user = User.builder()
                    .userRole(UserRole.CUSTOMER)
                    .username(username)
                    .isEmailVerified(true)
                    .email(email)
                    .password("Password")
                    .build();
            user = userRepository.save(user);
        } else {
            user = optionalUser.get();
        }
        userService.grantAccessToken(httpHeaders, user);
        userService.grantRefreshToken(httpHeaders, user);
        ResponseStructure<AuthResponse> responseStructure = new ResponseStructure<AuthResponse>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("User Verified")
                .setData(AuthResponse.builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .userRole(user.getUserRole())
                        .accessExpiration(accessExpirySeconds)
                        .refreshExpiration(refreshExpireSeconds)
                        .build());

        httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        ResponseEntity<ResponseStructure<AuthResponse>> responseEntity = ResponseEntity.status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(responseStructure);

        response.setStatus(HttpStatus.OK.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseEntity));

        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl("http://localhost:5173");
        super.onAuthenticationSuccess(request, response, authentication);
    }

}