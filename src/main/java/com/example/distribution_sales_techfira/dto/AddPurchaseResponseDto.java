package com.example.distribution_sales_techfira.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddPurchaseResponseDto extends BaseDTO {

    private String purchaseNumber;

    private Integer productCode;
    private String productDescription;
    private Integer quantityInCarton;
    private Integer quantityInPieces;
    private Integer unitOfMeasurement;
    private LocalDate date;
}
