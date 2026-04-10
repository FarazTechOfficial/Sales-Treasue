package com.example.distribution_sales_techfira.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankReqDTO extends BaseDTO {
    private String countryName;
    private String bankName;

}
