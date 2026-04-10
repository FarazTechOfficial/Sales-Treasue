package com.example.distribution_sales_techfira.dto;


import jakarta.validation.constraints.Email;

public class LoginReqDTO {

    @Email
    private String email;
    private String password;

    public LoginReqDTO() {
    }

    public LoginReqDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
