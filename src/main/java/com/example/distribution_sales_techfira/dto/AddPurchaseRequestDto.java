package com.example.distribution_sales_techfira.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddPurchaseRequestDto extends  BaseDTO{
    @NotNull
    private Integer productCode;
    @NotNull
    private Integer quantityInCarton;
    @NotNull
    private Integer quantityInPieces;
    @NotNull
    private Integer unitOfMeasurement;
    @NotNull
    @FutureOrPresent(message = "Date must be today or in the future")
    private LocalDate date;
}
