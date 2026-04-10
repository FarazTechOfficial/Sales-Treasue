package com.example.distribution_sales_techfira.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockAdjustmentRequestDto extends BaseDTO{
    private Integer productCode;
    private String productDescription;
    private Integer closingInventoryCarton;
    private Integer closingInventoryPiece;
    private Integer cartonAdjustment;
    private Integer piecesAdjustment;
}
