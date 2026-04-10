package com.example.distribution_sales_techfira.mapper;
import com.example.distribution_sales_techfira.dto.StockReportRequestDto;
import com.example.distribution_sales_techfira.dto.StockReportResponseDto;
<<<<<<< Updated upstream
import com.example.distribution_sales_techfira.entity.*;
import com.example.distribution_sales_techfira.util.Constants;
import org.springframework.stereotype.Component;
@Component
public class StockReportMapper {
=======
import com.example.distribution_sales_techfira.dto.VendorResDTO;
import com.example.distribution_sales_techfira.entity.*;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.util.Constants;
import org.springframework.stereotype.Component;

@Component
public class StockReportMapper {

>>>>>>> Stashed changes
        public StockReportResponseDto toDTO(AddPurchase entity) {
            StockReportResponseDto dto = new StockReportResponseDto();
            if (entity.getProduct() != null) {
                dto.setProductCode(entity.getProduct().getProductCode());
                dto.setProductDescription(entity.getProduct().getProductDescription());
            } else {
                dto.setProductCode(null);
                dto.setProductDescription(Constants.PRODUCT_NOT_FOUND);
            }
            dto.setQuantityInCarton(entity.getQuantityInCarton());
            dto.setQuantityInPieces(entity.getQunatityInPieces());
            return dto;
        }
    public StockReportResponseDto toDTO(AddPurchase entity, PurchaseReturn purchaseReturn, StockReport stockReport,StockAdjustment stockAdjustment) {
        StockReportResponseDto dto = new StockReportResponseDto();

        if (entity.getProduct() != null) {
            dto.setProductCode(entity.getProduct().getProductCode());
            dto.setProductDescription(entity.getProduct().getProductDescription());
        } else {
            dto.setProductCode(null);
            dto.setProductDescription(Constants.PRODUCT_NOT_FOUND);
        }
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
        // Set Purchase values
        int purchaseCartons = safeInt(entity.getQuantityInCarton());
        int purchasePieces = safeInt(entity.getQunatityInPieces());
        dto.setQuantityInCarton(purchaseCartons);
        dto.setQuantityInPieces(purchasePieces);
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
        // Set Return values
        int returnCartons = safeInt(purchaseReturn.getQuantityInCarton());
        int returnPieces = safeInt(purchaseReturn.getQuantityInPieces());
        dto.setReturnPurchaseCartoon(returnCartons);
        dto.setReturnPurchasePiece(returnPieces);
<<<<<<< Updated upstream
=======


>>>>>>> Stashed changes
        int saleCartons = safeInt(stockReport.getSaleCarton());
        int salePieces = safeInt(stockReport.getSalePieces());
        int adjustmentCartons = safeInt(stockAdjustment.getCartonAdjustment());
        int adjustmentPieces = safeInt(stockAdjustment.getPiecesAdjustment());
        int netPurchaseCarton = purchaseCartons - returnCartons;
        int netPurchasePieces = purchasePieces - returnPieces;
        int netSalesCarton=0;
        int netSalePieces=0;
        int closingCartons = (netPurchaseCarton - netSalesCarton) + adjustmentCartons;
        int closingPieces = (netPurchasePieces - netSalePieces) + adjustmentPieces;
        dto.setClosingInventoryCarton(closingCartons);
        dto.setClosingInventoryPiece(closingPieces);
        dto.setSaleCarton(saleCartons);
        dto.setSalePieces(salePieces);
        dto.setAdjustmentCarton(adjustmentCartons);
        dto.setAdjustmentPieces(adjustmentPieces);
        dto.setNetPurchaseCarton(netPurchaseCarton);
        dto.setNetPurchasePieces(netPurchasePieces);

        return dto;
    }

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    public StockReportResponseDto toDTO(StockReport entity) {
        StockReportResponseDto dto = new StockReportResponseDto();

        if (entity.getProduct() != null) {
            dto.setProductCode(entity.getProduct().getProductCode());
            dto.setProductDescription(entity.getProduct().getProductDescription());
        }

        dto.setQuantityInCarton(entity.getQuantityInCarton());
        dto.setQuantityInPieces(entity.getQuantityInPieces());
        dto.setReturnPurchaseCartoon(entity.getReturnPurchaseCartoon());
        dto.setReturnPurchasePiece(entity.getReturnPurchasePiece());
        dto.setSaleCarton(entity.getSaleCarton());
        dto.setSalePieces(entity.getSalePieces());
        dto.setClosingInventoryCarton(entity.getClosingInventoryCarton());
        dto.setClosingInventoryPiece(entity.getClosingInventoryPiece());
        dto.setAdjustmentCarton(entity.getAdjustmentCarton());
        dto.setAdjustmentPieces(entity.getAdjustmentPiece());
        dto.setNetPurchaseCarton(entity.getNetPurchaseCarton());
        dto.setNetPurchasePieces(entity.getNetPurchasePieces());
        dto.setNetSalePieces(entity.getNetSalePieces());
        dto.setNetSalesCarton(entity.getNetSalesCarton());

        return dto;
    }

    public StockReport toEntity(StockReportRequestDto dto,Product product) {
        StockReport stockReport = new StockReport();
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
        stockReport.setProduct(product);
        stockReport.setProductDescription(dto.getProductDescription());
        stockReport.setQuantityInCarton(dto.getQuantityInCarton());
        stockReport.setQuantityInPieces(dto.getQuantityInPieces());
        stockReport.setReturnPurchaseCartoon(dto.getReturnPurchaseCartoon());
        stockReport.setReturnPurchasePiece(dto.getReturnPurchasePiece());
        stockReport.setSaleCarton(dto.getSaleCarton());
        stockReport.setSalePieces(dto.getSalePieces());
        stockReport.setAdjustmentCarton(dto.getAdjustmentCarton());
        stockReport.setAdjustmentPiece(dto.getAdjustmentPiece());
        stockReport.setNetPurchaseCarton(dto.getNetPurchaseCarton());
        stockReport.setNetPurchasePieces(dto.getNetPurchasePieces());
        stockReport.setClosingInventoryCarton(dto.getClosingInventoryCarton());
        stockReport.setClosingInventoryPiece(dto.getClosingInventoryPiece());
        stockReport.setNetSalePieces(dto.getNetSalePieces());
        stockReport.setNetSalesCarton(dto.getNetSalesCarton());
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
        return stockReport;
    }
    private int safeInt(Integer value) {
        return value != null ? value : 0;
    }
}

