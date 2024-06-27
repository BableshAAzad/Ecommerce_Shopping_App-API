package com.ecommerce.shopping.jwt;

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

//    private String secret = "hIzFEyWuntUij2Z5xparLC7epKalTAwj/CjwbUkPDRA5ovkGH9tKMaeYwwA8MNzq29TXbRQGZ1/w\nYKtiO1OrNA==";
private String secret = "6rOtQA19omYEnaf5Hb3bjxetQX/7S0ZkA4lVdFOxGMLTI4QPFhFtnHQpSxr5IrsLcrvNJL/8Y8Ht\nvhlGnW67Qg==";

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

}
