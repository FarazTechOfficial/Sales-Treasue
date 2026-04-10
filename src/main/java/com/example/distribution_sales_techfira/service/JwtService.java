package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.LoginReqDTO;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;

public interface JwtService {

    String generate(LoginReqDTO loginReqDTO);
    SecretKey generateKey();
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
    Date extractExpiration(String token);

}