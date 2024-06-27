package com.ecommerce.shopping.filter;

import com.ecommerce.shopping.exception.IllegalOperationException;
import com.ecommerce.shopping.exception.UserNotExistException;
import com.ecommerce.shopping.user.entity.User;
import com.ecommerce.shopping.user.repositoty.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class ClientApiKeyFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getSession(false) != null)
            throw new IllegalOperationException("Please logout first....!!!");

        if (!request.getRequestURI().equals("/api/v1/sellers/register") || !request.getRequestURI().equals("/api/v1/customers/register")) {
            String apiKey = request.getHeader("API-KEY");
            String username = request.getHeader("USERNAME");

            if (apiKey != null || username != null) {
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UserNotExistException("Username: " + username + ", does not exist"));
//                if (!apiKey.equals(user.getApiKey())) {
//                    throw new BadCredentialsException("Invalid Credential");
//                }
            } else
                throw new UserNotExistException("User not found");
        }
        filterChain.doFilter(request, response);
    }
}
