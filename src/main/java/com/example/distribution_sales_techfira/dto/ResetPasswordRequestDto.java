package com.example.distribution_sales_techfira.dto;

import jakarta.validation.constraints.NotBlank;

public class ResetPasswordRequestDto {
    @NotBlank(message = "Token is required")private
    String token;@NotBlank(message = "Password is required")
    private String newPassword;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public ResetPasswordRequestDto(String token, String newPassword) {
        this.token = token;    this.newPassword = newPassword;
    }

}
