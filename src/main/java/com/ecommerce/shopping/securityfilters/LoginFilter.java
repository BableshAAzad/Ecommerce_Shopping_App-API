package com.ecommerce.shopping.securityfilters;

import com.ecommerce.shopping.utility.FilterExceptionHandle;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class LoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        boolean loggedIn = false;
        if (cookies != null)
            for(Cookie cookie : cookies){
                if (cookie.getName().equals("at") || cookie.getName().equals("rt"))
                    loggedIn = true;
            }

        if (loggedIn) {
            FilterExceptionHandle.handleJwtExpire(response,
                    HttpStatus.UNAUTHORIZED.value(),
                    "Failed to Login",
                    "Please Logout first");
            return;
        }
        else
            filterChain.doFilter(request, response);
    }
}
