package com.example.distribution_sales_techfira.dto;

public class LoginResponseDTO {
    private String token;
    private String username;
    private String role;
    private boolean success;
    private String message;

    public LoginResponseDTO (){}

    public LoginResponseDTO(String token, String username, String role, boolean success, String message) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.success=success;
        this.message=message;
    }

    // Getters and setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
