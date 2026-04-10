package com.example.distribution_sales_techfira.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    SecretKey  jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expiration = 15 * 60 * 1000; //15 mints

    public String generateResetToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("purpose", "reset_password")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(jwtSecret)
                .compact();
    }


    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired token");
        }
    }
}
