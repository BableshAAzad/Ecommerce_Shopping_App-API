package com.ecommerce.shopping.securityfilters;

import com.ecommerce.shopping.exception.InvalidJwtTokenException;
import com.ecommerce.shopping.exception.JwtExpiredException;
import com.ecommerce.shopping.jwt.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@AllArgsConstructor
public class JwtOauthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null) {
            token = token.substring(7);

            try {
                String username = jwtService.extractUserName(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, null, null);
                    upat.setDetails(new WebAuthenticationDetails(request));

//              we store inside security context holder
                    SecurityContextHolder.getContext().setAuthentication(upat);
                }
            } catch (JwtException e) {
                e.fillInStackTrace();
                throw new InvalidJwtTokenException("Invalid Jwt Token Exception");
            }

            try {
                Date expireDate = jwtService.extractExpirationDate(token);
            } catch (ExpiredJwtException e) {
                e.fillInStackTrace();
                throw new JwtExpiredException("Token has been expired");
            }
        }
        filterChain.doFilter(request, response);
    }
}
