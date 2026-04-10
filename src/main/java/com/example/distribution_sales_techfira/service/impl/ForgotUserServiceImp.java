package com.example.distribution_sales_techfira.service.impl;


import com.example.distribution_sales_techfira.entity.User;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.repository.UserRepository;
import com.example.distribution_sales_techfira.security.JwtUtil;
import com.example.distribution_sales_techfira.service.ForgotEmailService;
import com.example.distribution_sales_techfira.service.ForgotUserService;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ForgotUserServiceImp implements ForgotUserService {
    private final JwtUtil jwtUtil;
    private final  ForgotEmailService emailService;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

   // @Value("${app.base-url}")
    private String baseUrl;

    public ForgotUserServiceImp(JwtUtil jwtUtil, ForgotEmailService emailService, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
    public void sendResetLink(String email) throws MessagingException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new CustomException("Email does not exist");
        }

        String token = jwtUtil.generateResetToken(user.getEmail());
        String link = "http://localhost:4200/reset-password?token=" + token;

        emailService.sendSimpleResetEmail(user.getEmail(), link);
    }

    public void resetPassword(String token, String newPassword) {
        Claims claims = jwtUtil.validateToken(token);

        if (!"reset_password".equals(claims.get("purpose"))) {
            throw new RuntimeException("Invalid token purpose");
        }

        String email = claims.getSubject();
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new CustomException("Email does not exist");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

}