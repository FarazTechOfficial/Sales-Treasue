package com.example.distribution_sales_techfira.dto;

import com.example.distribution_sales_techfira.entity.Company;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class LicenseResDTO extends BaseDTO{


    private Company company;
    private LocalDate ValidFrom;
    private LocalDate validTo;
    private Integer numUsers;
    
}
