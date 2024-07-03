package com.ecommerce.shopping.securityfilters;

import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.jwt.JwtService;
import com.ecommerce.shopping.utility.FilterExceptionHandle;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String rt = null;
        String at = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("rt"))
                    rt = cookie.getValue();
                else if (cookie.getName().equals("at"))
                    at = cookie.getValue();
            }
        }
        if (at != null) {
            try {
                Date expireDate = jwtService.extractExpirationDate(at);
                String username = jwtService.extractUserName(at);
                UserRole userRole = jwtService.extractUserRole(at);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority(userRole.name())));
                    upat.setDetails(new WebAuthenticationDetails(request));
//              we store inside security context holder
                    SecurityContextHolder.getContext().setAuthentication(upat);
                }
            } catch (ExpiredJwtException e) {
                FilterExceptionHandle.handleJwtExpire(response,
                        HttpStatus.UNAUTHORIZED.value(),
                        "Failed to authenticate",
                        "Token has already expired");
                return;
            } catch (JwtException e) {
                FilterExceptionHandle.handleJwtExpire(response,
                        HttpStatus.UNAUTHORIZED.value(),
                        "Failed to authenticate",
                        "you are not allowed to access this resource");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
