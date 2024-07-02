package com.ecommerce.shopping.securityfilters;

import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.exception.InvalidJwtTokenException;
import com.ecommerce.shopping.exception.JwtExpiredException;
import com.ecommerce.shopping.jwt.JwtService;
import com.ecommerce.shopping.utility.ErrorStructure;
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
                    rt = cookie.getName();
                else if (cookie.getName().equals("at"))
                    at = cookie.getName();
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
//                e.fillInStackTrace();
//                throw new JwtExpiredException("Token has been expired");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(new ErrorStructure<String>()
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setMessage("Token has Expired")
                        .setRootCause("Token has expired please generate login again")
                        .toString());
                return;
            } catch (JwtException e) {
                e.fillInStackTrace();
                throw new InvalidJwtTokenException("Invalid Jwt Token Exception");
            }
        }

        filterChain.doFilter(request, response);
    }
}
