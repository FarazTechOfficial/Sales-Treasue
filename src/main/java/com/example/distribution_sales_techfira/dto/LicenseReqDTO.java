package com.example.distribution_sales_techfira.dto;


import com.example.distribution_sales_techfira.entity.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class LicenseReqDTO extends BaseDTO{


    @NotNull
    private Company company;

    private LocalDate validFrom;
    private LocalDate validTo;
    private Integer numUsers;


}
