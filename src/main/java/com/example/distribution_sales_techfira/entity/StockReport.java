package com.example.distribution_sales_techfira.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockReport extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
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
    private Integer adjustmentPiece;
    private Integer netPurchaseCarton;
    private Integer netPurchasePieces;
    private Integer netSalesCarton;
    private Integer netSalePieces;
}
