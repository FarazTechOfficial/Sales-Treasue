package com.example.distribution_sales_techfira.dto;

public class RegistrationResponseDTO {
    private boolean success;
    private String message;
    private UserResDTO user;

    public RegistrationResponseDTO() {
    }

    public RegistrationResponseDTO(boolean success, String message, UserResDTO user) {
        this.success = success;
        this.message = message;
        this.user = user;
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

    public UserResDTO getUser() {
        return user;
    }

    public void setUser(UserResDTO user) {
        this.user = user;
    }
}