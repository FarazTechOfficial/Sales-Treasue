package com.example.distribution_sales_techfira.controller;


import com.example.distribution_sales_techfira.service.AuthService;
import com.example.distribution_sales_techfira.service.TokenBlacklist;
import com.example.distribution_sales_techfira.service.impl.TokenBlacklistService;
import com.example.distribution_sales_techfira.service.impl.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.distribution_sales_techfira.dto.*;

@RestController

public class AuthController {

    private final AuthService authService;
    private final TokenBlacklist tokenBlacklist;

    public AuthController(AuthService authService, TokenBlacklist tokenBlacklist) {
        this.authService = authService;
        this.tokenBlacklist = tokenBlacklist;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginReqDTO userReqDTO){
        LoginResponseDTO response = authService.verify(userReqDTO);
        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("No token provided");
        }

        String token = authHeader.substring(7);
        tokenBlacklist.addToBlacklist(token);
        return ResponseEntity.ok("Logged out successfully");
    }

}
