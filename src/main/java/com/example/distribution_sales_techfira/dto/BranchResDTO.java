package com.example.distribution_sales_techfira.dto;

import com.example.distribution_sales_techfira.entity.Branch;
import com.example.distribution_sales_techfira.entity.Company;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class BranchResDTO extends BaseDTO{


    private String name;
    private String email;
    private String number;
    private Company company;
    private List<Branch> branches;

}
