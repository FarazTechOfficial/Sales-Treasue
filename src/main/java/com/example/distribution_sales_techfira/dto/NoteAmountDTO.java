package com.example.distribution_sales_techfira.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteAmountDTO extends BaseDTO{

    @NotNull
    private Integer noteId;

    private String noteName;

    @NotNull
    private Double amount;



}
