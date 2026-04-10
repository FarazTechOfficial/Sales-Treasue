package com.example.distribution_sales_techfira.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "purchase_returns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReturn extends BaseEntity{

    private String purchaseNumber;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantityInCarton;

    private Integer quantityInPieces;

    private Integer unitOfMeasurement;

    private LocalDate date;
}
