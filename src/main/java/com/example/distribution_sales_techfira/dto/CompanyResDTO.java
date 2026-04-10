package com.example.distribution_sales_techfira.dto;

import com.example.distribution_sales_techfira.entity.Branch;
import com.example.distribution_sales_techfira.entity.License;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CompanyResDTO extends BaseDTO{


    private String name;
    private String email;
    private String phone;
    private List<Branch> branches;
    private License licence;
    private String address;


}
