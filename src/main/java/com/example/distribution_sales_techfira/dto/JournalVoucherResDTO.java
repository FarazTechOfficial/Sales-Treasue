package com.example.distribution_sales_techfira.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JournalVoucherResDTO extends BaseDTO{


    private LocalDate transactionDate;
    private String description;
    // private Integer companyId;
    private CompanyResDTO company; // Replace this:

    private NoteAmountDTO creditNote;
    private NoteAmountDTO debitNote;


}
