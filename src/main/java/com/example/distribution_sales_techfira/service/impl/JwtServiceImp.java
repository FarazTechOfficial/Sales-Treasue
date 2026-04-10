package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.LoginReqDTO;
import com.example.distribution_sales_techfira.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImp implements JwtService {

    private String secretKey;

    @Override
    public String generate(LoginReqDTO loginReqDTO) {
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .setSubject(loginReqDTO.getEmail())
                .issuer("Tech Fira")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .and()
                .signWith(generateKey())
                .compact();
    }

    @Override
    public SecretKey generateKey(){
        byte[] decoded = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decoded);
    }



    public String getSecretKey(){
        return this.secretKey = "Y2hhbWJlcmR1Y2tzdHJldGNodmlld3N0cnVja3Ntb2tlY2hpbGRyZW52YXJpZXR5YmU=";
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }


    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }


    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    @Override
    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }
}
