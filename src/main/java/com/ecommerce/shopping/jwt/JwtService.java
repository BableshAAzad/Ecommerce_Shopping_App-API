package com.ecommerce.shopping.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

private String secret = "cB2d4a60KtbSqSx85awIWt0+kKUrngR9viqlavYnPq3NBwgIBbp3ZFo7rFoM+OSjRBcoO40MxQ0K2mHFJFgDjA==";

    public String createJwtToken(String username, long expirationTimeInMillis) {
        return Jwts.builder()
                .setClaims(Map.of())
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSignatureKey() {
        byte[] key = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(key);
    }

  private Claims passJwtToken(String token){
      return Jwts.parserBuilder()
              .setSigningKey(getSignatureKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
  }

  public String extractUserName(String token){
     return passJwtToken(token).getSubject();
  }

  public Date extractIssueDate(String token){
        return passJwtToken(token).getIssuedAt();
  }

  public Date extractExpirationDate(String token){
        return passJwtToken(token).getExpiration();
  }

}
