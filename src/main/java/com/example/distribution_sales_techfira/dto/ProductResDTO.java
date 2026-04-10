package com.example.distribution_sales_techfira.dto;

import lombok.Data;

@Data
public class ProductResDTO extends BaseDTO{
    private Integer productCode;
    private String productDescription;
    private Float unitOfMeasurement;
    private Float distributorPrice;
    private Float tradePrice;
    private Float maximumRetailPrice;
    private Float quantityOpInPieces;
    private Float quantityOpInCarton;

}
