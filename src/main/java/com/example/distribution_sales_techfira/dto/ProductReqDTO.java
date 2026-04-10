package com.example.distribution_sales_techfira.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReqDTO extends BaseDTO{
    @NotNull
    private Integer productCode;
    @NotNull
    private String productDescription;
    @NotNull
    private Float unitOfMeasurement;
    @NotNull
    private Float distributorPrice;
    @NotNull
    private Float tradePrice;
    @NotNull
    private Float maximumRetailPrice;
    @NotNull
    private Float quantityOpInPieces;
    @NotNull
    private Float quantityOpInCarton;
}
