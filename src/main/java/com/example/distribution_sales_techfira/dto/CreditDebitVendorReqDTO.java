package com.example.distribution_sales_techfira.dto;

import com.example.distribution_sales_techfira.entity.Note;
import com.example.distribution_sales_techfira.entity.Vendor;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
public class CreditDebitVendorReqDTO extends BaseDTO {

    private Vendor vendor;
    @NotNull
    private LocalDate transactionDate;
    @NotNull
    Float amount;
    @NotNull
    private String description;
    private Note creditNote;
    @NotNull
    private Note debitNote;
    private String transactionType;


}
