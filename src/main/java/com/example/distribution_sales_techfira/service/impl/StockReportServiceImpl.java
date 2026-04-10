package com.example.distribution_sales_techfira.service.impl;
import com.example.distribution_sales_techfira.dto.StockReportRequestDto;
import com.example.distribution_sales_techfira.dto.StockReportResponseDto;
import com.example.distribution_sales_techfira.entity.*;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.StockReportMapper;
import com.example.distribution_sales_techfira.repository.*;
import com.example.distribution_sales_techfira.service.StockReportService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import com.example.distribution_sales_techfira.util.Constants;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockReportServiceImpl implements StockReportService {

    private final ProductRepository productRepository;
    private  final PurchaseRepository purchaseRepository;
    private final StockReportMapper mapper;
    private  final PurchaseReturnRepository purchaseReturnRepository;
    private final AuditUtil auditUtil;
    private final StockAdjustmentRepository stockAdjustmentRepository;
    private  final StockReportRepository stockReportRepository;
    public StockReportServiceImpl(ProductRepository productRepository, PurchaseRepository purchaseRepository, StockReportMapper mapper, PurchaseReturnRepository purchaseReturnRepository, AuditUtil auditUtil, StockAdjustmentRepository stockAdjustmentRepository, StockReportRepository stockReportRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
        this.mapper = mapper;
        this.purchaseReturnRepository = purchaseReturnRepository;
        this.auditUtil = auditUtil;
        this.stockAdjustmentRepository = stockAdjustmentRepository;
        this.stockReportRepository = stockReportRepository;
    }

    @Override
    public StockReportResponseDto findByID(Integer id) {

        AddPurchase purchase = purchaseRepository.findById(id).orElseThrow(() -> new CustomException(Constants.PRODUCT_NOT_FOUND));
        return mapper.toDTO(purchase);

    }

    @Override
    public List<StockReportResponseDto> findAllProductCode(){

        List<Product> products = productRepository.findByStatusNot(3);

        return products.stream().map(product -> {
            StockReportResponseDto dto = new StockReportResponseDto();
            dto.setProductCode(product.getProductCode());
            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public List<StockReportResponseDto> findByCodeAndDate(Integer productCode, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59, 999_999_999);
        AddPurchase addPurchases = purchaseRepository.findTopByProduct_ProductCodeAndStatusNotAndCreatedAtBetweenOrderByCreatedAtDesc(productCode,3,startOfDay, endOfDay);
        PurchaseReturn purchaseReturn = purchaseReturnRepository.findTopByProduct_ProductCodeAndStatusNotAndCreatedAtBetweenOrderByCreatedAtDesc(productCode,3,startOfDay, endOfDay);
        StockReport stockReport = stockReportRepository.findTopByProduct_ProductCodeOrderByCreatedAtDesc(productCode);
        StockAdjustment stockAdjustment = stockAdjustmentRepository.findTopByProduct_ProductCodeOrderByCreatedAtDesc(productCode);

        StringBuilder errorMessage = new StringBuilder();

        if (addPurchases == null) {
            errorMessage.append("AddPurchase not found. ");
        }
        if (purchaseReturn == null) {
            errorMessage.append("PurchaseReturn not found. ");
        }
        if (stockReport == null) {
            errorMessage.append("StockReport not found. ");
        }
        if (stockAdjustment == null) {
            errorMessage.append("StockAdjustment not found. ");
        }
        StockReportResponseDto data = mapper.toDTO(addPurchases, purchaseReturn,stockReport,stockAdjustment);
        return  List.of(data);
    }
    @Override
    public StockReportResponseDto save(StockReportRequestDto dto) {
        auditUtil.createAudit(dto);
        Product product = productRepository.findByProductCode(dto.getProductCode())
                .orElseThrow(() -> new CustomException(Constants.PRODUCT_NOT_FOUND));
        StockReport stockReport = mapper.toEntity(dto,product);
        StockReport saved = stockReportRepository.save(stockReport);
        return mapper.toDTO(saved);
    }
//
//    @Override
//    public StockReportResponseDto update(Integer id, StockReportRequestDto dto) {
//        StockReport existing = stockReportRepository.findById(id)
//                .orElseThrow(() -> new CustomException("StockReport ID does not exist"));
//        auditUtil.updateAudit(dto);
//        applyUpdates(existing, dto);
//        recalculateClosingInventory(existing);
//        StockReport updated = stockReportRepository.save(existing);
//        return mapper.toDTO(updated);
//    }
//    private void applyUpdates(StockReport entity, StockReportRequestDto dto) {
//        if (dto.getProductDescription() != null)
//            entity.setProductDescription(dto.getProductDescription());
//        entity.setQuantityInCarton(safeInt(dto.getQuantityInCarton()));
//        entity.setQuantityInPieces(safeInt(dto.getQuantityInPieces()));
//        entity.setReturnPurchaseCartoon(safeInt(dto.getReturnPurchaseCartoon()));
//        entity.setReturnPurchasePiece(safeInt(dto.getReturnPurchasePiece()));
//        entity.setSaleCarton(safeInt(dto.getSaleCarton()));
//        entity.setSalePieces(safeInt(dto.getSalePieces()));
//    }
//
//    private void recalculateClosingInventory(StockReport entity) {
//        int cartons = defaultZero(entity.getQuantityInCarton()) - defaultZero(entity.getSaleCarton());
//        int pieces = defaultZero(entity.getQuantityInPieces()) - defaultZero(entity.getSalePieces());
//        entity.setClosingInventoryCarton(cartons);
//        entity.setClosingInventoryPiece(pieces);
//    }


}
