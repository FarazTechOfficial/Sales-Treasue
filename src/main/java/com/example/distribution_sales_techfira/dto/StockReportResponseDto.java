package com.example.distribution_sales_techfira.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockReportResponseDto extends BaseDTO {
    private Integer productCode;
    private String productDescription;
    private Integer quantityInCarton;
    private Integer quantityInPieces;
    private Integer returnPurchaseCartoon;
    private Integer returnPurchasePiece;
    private Integer saleCarton;
    private Integer salePieces;
    private Integer closingInventoryCarton;
    private Integer closingInventoryPiece;
    private Integer adjustmentCarton;
    private Integer adjustmentPieces;
    private Integer netPurchaseCarton;
    private Integer netPurchasePieces;
    private Integer netSalesCarton;
    private Integer netSalePieces;

}
