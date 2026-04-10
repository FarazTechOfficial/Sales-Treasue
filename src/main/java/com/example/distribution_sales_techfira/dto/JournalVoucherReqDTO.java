package com.example.distribution_sales_techfira.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JournalVoucherReqDTO extends BaseDTO{

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;
    private String description;
    @NotNull
    private CompanyReqDTO company;
    @Valid
    @NotNull
    private NoteAmountDTO creditNote;
    @Valid
    @NotNull
    private NoteAmountDTO debitNote;

}
