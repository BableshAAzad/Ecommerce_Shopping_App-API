package com.ecommerce.shopping.jwt;

import com.ecommerce.shopping.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${application.jwt.secrete}")
    private String secretJwt;

    private static final String ROLE = "role";

    public String createJwtToken(String username, UserRole role, long expirationTimeInMillis) {
        return Jwts.builder()
                .setClaims(Map.of(ROLE, role))
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSignatureKey() {
        byte[] key = Decoders.BASE64.decode(secretJwt);
        return Keys.hmacShaKeyFor(key);
    }

    private Claims passJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserName(String token) {
        return passJwtToken(token).getSubject();
    }

    public Date extractIssueDate(String token) {
        return passJwtToken(token).getIssuedAt();
    }

    public Date extractExpirationDate(String token) {
        return passJwtToken(token).getExpiration();
    }

    public UserRole extractUserRole(String token){
        String role = passJwtToken(token).get(ROLE, String.class);
        return UserRole.valueOf(role);
    }

}
