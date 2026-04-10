package com.example.distribution_sales_techfira.service;
import jakarta.mail.MessagingException;

public interface ForgotEmailService {
    void sendSimpleResetEmail(String to, String token) throws MessagingException;
}