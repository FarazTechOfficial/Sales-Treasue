package com.example.distribution_sales_techfira.mapper;
<<<<<<< Updated upstream
import com.example.distribution_sales_techfira.dto.StockAdjustmentRequestDto;
import com.example.distribution_sales_techfira.dto.StockAdjustmentResponseDto;
import com.example.distribution_sales_techfira.entity.*;
=======

import com.example.distribution_sales_techfira.dto.StockAdjustmentRequestDto;
import com.example.distribution_sales_techfira.dto.StockAdjustmentResponseDto;
import com.example.distribution_sales_techfira.dto.StockReportRequestDto;
import com.example.distribution_sales_techfira.dto.StockReportResponseDto;
import com.example.distribution_sales_techfira.entity.*;
import com.example.distribution_sales_techfira.exception.CustomException;
>>>>>>> Stashed changes
import com.example.distribution_sales_techfira.util.Constants;
import org.springframework.stereotype.Component;

@Component
public class StockAdjustmentMapper {
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    public StockAdjustmentResponseDto toDTO(AddPurchase entity,PurchaseReturn purchaseReturn, StockReport stockReport, StockAdjustment stockAdjustment) {
        StockAdjustmentResponseDto dto = new StockAdjustmentResponseDto();
        if (entity.getProduct() != null) {
            dto.setProductCode(entity.getProduct().getProductCode());
            dto.setProductDescription(entity.getProduct().getProductDescription());
        } else {
            dto.setProductCode(null);
            dto.setProductDescription(Constants.PRODUCT_NOT_FOUND);
        }
        int purchaseCartons = safeInt(entity.getQuantityInCarton());
        int purchasePieces = safeInt(entity.getQunatityInPieces());
        // Set Return values
        int returnCartons = safeInt(purchaseReturn.getQuantityInCarton());
        int returnPieces = safeInt(purchaseReturn.getQuantityInPieces());
<<<<<<< Updated upstream
        int adjustmentCartons = safeInt(stockAdjustment.getCartonAdjustment());
        int adjustmentPieces = safeInt(stockAdjustment.getPiecesAdjustment());
=======

        int adjustmentCartons = safeInt(stockAdjustment.getCartonAdjustment());
        int adjustmentPieces = safeInt(stockAdjustment.getPiecesAdjustment());

>>>>>>> Stashed changes
        int saleCartons = safeInt(stockReport.getSaleCarton());//later will be connected to sales module
        int salePieces = safeInt(stockReport.getSalePieces());
        int netPurchaseCarton = purchaseCartons - returnCartons;
        int netPurchasePieces = purchasePieces - returnPieces;
        int netSalesCarton=0;
        int netSalePieces=0;
        int closingCartons = (netPurchaseCarton - netSalesCarton) + adjustmentCartons;
        int closingPieces = (netPurchasePieces - netSalePieces) + adjustmentPieces;
        dto.setClosingInventoryCarton(closingCartons);
        dto.setClosingInventoryPiece(closingPieces);
        dto.setCartonAdjustment(adjustmentCartons);
        dto.setPiecesAdjustment(adjustmentPieces);
        return dto;
    }

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    public StockAdjustment toEntity(StockAdjustmentRequestDto dto, Product product) {
        StockAdjustment entity = new StockAdjustment();
            entity.setProduct(product);
           entity.setClosingInventoryCarton(dto.getClosingInventoryCarton());
           entity.setClosingInventoryPiece(dto.getClosingInventoryPiece());
           entity.setCartonAdjustment(dto.getCartonAdjustment());
           entity.setPiecesAdjustment(dto.getPiecesAdjustment());
        return entity;
    }
    public StockAdjustmentResponseDto toDTO(StockAdjustment entity) {
        if (entity == null) {
            return null;
        }

        StockAdjustmentResponseDto dto = new StockAdjustmentResponseDto();

        if (entity.getProduct() != null) {
            dto.setProductCode(entity.getProduct().getProductCode());
            dto.setProductDescription(entity.getProduct().getProductDescription());
        } else {
            dto.setProductCode(null);
            dto.setProductDescription(Constants.PRODUCT_NOT_FOUND);
        };
        dto.setCartonAdjustment(entity.getCartonAdjustment());
        dto.setPiecesAdjustment(entity.getPiecesAdjustment());
        dto.setClosingInventoryCarton(entity.getClosingInventoryCarton());
        dto.setClosingInventoryPiece(entity.getClosingInventoryPiece());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        return dto;
    }
<<<<<<< Updated upstream
=======


>>>>>>> Stashed changes
    private int safeInt(Integer value) {
        return value != null ? value : 0;
    }

}
