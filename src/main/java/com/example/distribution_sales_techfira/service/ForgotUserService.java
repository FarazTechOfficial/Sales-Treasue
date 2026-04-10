package com.example.distribution_sales_techfira.service;


import jakarta.mail.MessagingException;

public interface ForgotUserService {
    void sendResetLink(String email) throws MessagingException;
    void resetPassword(String token, String newPassword);
}
