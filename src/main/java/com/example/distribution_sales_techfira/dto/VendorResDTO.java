package com.example.distribution_sales_techfira.dto;

import com.example.distribution_sales_techfira.entity.Bank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorResDTO extends BaseDTO {

   // private Integer id;
    private String vendorCode;
    private String vendorName;
    private String contactNumber;
    private String address;
    private String emailAddress;
    private Bank bank;
    private String accountNumber;
    //private Integer status;


}
