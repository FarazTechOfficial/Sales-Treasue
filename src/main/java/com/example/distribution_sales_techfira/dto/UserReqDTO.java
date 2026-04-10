package com.example.distribution_sales_techfira.dto;


import com.example.distribution_sales_techfira.entity.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserReqDTO {

    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private Role role;
    @NotBlank
    @Size(min = 6, message = "Password must be greater than 6")
    private String password;
    private Integer companyId;
    private Integer branchId;


    public UserReqDTO() {
    }


    public UserReqDTO(String name, String email, Role role, String password) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }
}