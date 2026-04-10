package com.example.distribution_sales_techfira.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AddPurchase extends  BaseEntity{

    private String purchaseNumber;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantityInCarton;
    private Integer qunatityInPieces;
    private Integer unitOfMeasurement;
    private LocalDate date;


}
