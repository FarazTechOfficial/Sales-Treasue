package com.example.distribution_sales_techfira.dto;

import com.example.distribution_sales_techfira.entity.Note;
import com.example.distribution_sales_techfira.entity.Vendor;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
public class CreditDebitVendorResDTO extends BaseDTO{

    private Vendor vendor;
    private LocalDate transactionDate;
    Float amount;
    private String description;
    private Note creditNote;
    private Note debitNote;
    private String transactionType;


}

