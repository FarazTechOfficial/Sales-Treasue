package com.example.distribution_sales_techfira.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Product extends BaseEntity{
    @Column(unique=true)
    private Integer productCode;
    private String productDescription;
    private Float unitOfMeasurement;
    private Float distributorPrice;
    private Float tradePrice;
    private Float maximumRetailPrice;
    private Float quantityOpInPieces;
    private Float quantityOpInCarton;
}
