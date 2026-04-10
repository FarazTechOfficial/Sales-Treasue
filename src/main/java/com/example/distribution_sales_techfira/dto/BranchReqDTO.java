package com.example.distribution_sales_techfira.dto;


import com.example.distribution_sales_techfira.entity.Company;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchReqDTO extends BaseDTO{


    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String number;
    @NotNull
    private Company company;

}
