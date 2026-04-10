package com.example.distribution_sales_techfira.entity;
<<<<<<< Updated upstream
=======
import com.fasterxml.jackson.annotation.JsonBackReference;
>>>>>>> Stashed changes
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockAdjustment extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer productCode;
    private String productDescription;
    private Integer closingInventoryCarton;
    private Integer closingInventoryPiece;
    private Integer cartonAdjustment;
    private Integer piecesAdjustment;
}
