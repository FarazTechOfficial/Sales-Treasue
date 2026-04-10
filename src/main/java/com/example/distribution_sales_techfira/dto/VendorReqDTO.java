package com.example.distribution_sales_techfira.dto;


import com.example.distribution_sales_techfira.entity.Bank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorReqDTO extends BaseDTO{

//    private Integer id;
    @NotBlank
    private String vendorName;
    @NotBlank
    private String contactNumber;
    @NotBlank
    private String address;
    @NotBlank
    @Email
    private String emailAddress;
    private Bank bank;  // Referencing Bank entity
//    private Integer status;
    private String accountNumber;
    private String vendorCode;





}
