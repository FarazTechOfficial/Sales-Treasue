package com.example.distribution_sales_techfira.dto;

import com.example.distribution_sales_techfira.entity.Branch;
import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.entity.Role;

import java.time.LocalDate;

public class UserFullResDTO {
    private Integer id;
    private String name;
    private String email;
    private int status;
    private LocalDate createdAt;
    private Role role;

    private Company company;
    private Branch branch;

    public UserFullResDTO() {
    }

    public UserFullResDTO(Integer id, String name, String email, int status, Role role, LocalDate createdAt) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.status=status;
        this.role = role;
        this.createdAt = createdAt;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
