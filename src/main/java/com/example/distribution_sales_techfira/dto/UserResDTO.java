package com.example.distribution_sales_techfira.dto;

import java.time.LocalDate;

public class UserResDTO {

    private Integer id;
    private String name;
    private String email;
    private Integer status;
    private String role;
    private String companyName;
    private String branchName;
    private LocalDate createdAt;


    public UserResDTO() {
    }

    public UserResDTO(String name, String email, Integer status, String role, String companyName, String branchName, LocalDate createdAt) {
        this.name = name;
        this.email = email;
        this.status=status;
        this.role=role;
        this.companyName=companyName;
        this.branchName=branchName;
        this.createdAt=createdAt;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}