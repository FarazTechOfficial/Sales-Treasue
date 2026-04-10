package com.example.distribution_sales_techfira.controller;

import com.example.distribution_sales_techfira.dto.ForgotPasswordRequestDto;
import com.example.distribution_sales_techfira.dto.ResetPasswordRequestDto;
import com.example.distribution_sales_techfira.service.ForgotUserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ForgotEmailController {
    @Autowired
    private ForgotUserService userService;

//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> payload) throws MessagingException {
//        String email = payload.get("email");
//        userService.sendResetLink(email);
//        return ResponseEntity.ok("Reset link sent to email.");
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
//        String token = payload.get("token");
//        String newPassword = payload.get("newPassword");
//
//        userService.resetPassword(token, newPassword);
//        return ResponseEntity.ok("Password reset successfully.");
//    }
@PostMapping("/forgot-password")
public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDto request) throws MessagingException {
    userService.sendResetLink(request.getEmail());
    return ResponseEntity.ok("Reset link sent to email.");
}

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDto request) {
        userService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Password reset successfully.");
    }
}
