package com.example.distribution_sales_techfira.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReturnReqDTO extends BaseDTO{

    @NotNull
    private Integer productCode;

    @NotNull
    private Integer quantityInCarton;

    @NotNull
    private Integer quantityInPieces;

    @NotNull
    private Integer unitOfMeasurement;

    @NotNull
    @FutureOrPresent(message = "Date must be of today or future.")
    private LocalDate date;
}
