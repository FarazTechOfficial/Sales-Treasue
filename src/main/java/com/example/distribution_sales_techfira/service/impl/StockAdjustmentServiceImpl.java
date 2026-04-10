package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.StockAdjustmentRequestDto;
import com.example.distribution_sales_techfira.dto.StockAdjustmentResponseDto;
import com.example.distribution_sales_techfira.dto.StockReportRequestDto;
import com.example.distribution_sales_techfira.dto.StockReportResponseDto;
import com.example.distribution_sales_techfira.entity.*;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.StockAdjustmentMapper;
import com.example.distribution_sales_techfira.repository.*;
import com.example.distribution_sales_techfira.service.StockAdjustmentService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import com.example.distribution_sales_techfira.util.Constants;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockAdjustmentServiceImpl implements StockAdjustmentService {

    private final ProductRepository productRepository;
    private  final PurchaseRepository purchaseRepository;
    private  final StockAdjustmentMapper stockAdjustmentMapper;
    private final StockAdjustmentRepository stockAdjustmentRepository;
    private final StockReportRepository stockReportRepository;
    private  final PurchaseReturnRepository purchaseReturnRepository;
    private final AuditUtil auditUtil;


    public StockAdjustmentServiceImpl(ProductRepository productRepository, PurchaseRepository purchaseRepository, StockAdjustmentMapper stockAdjustmentMapper, StockAdjustmentRepository stockAdjustmentRepository, PurchaseReturnRepository purchaseReturnRepository, AuditUtil auditUtil, StockReportRepository stockReportRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
        this.stockAdjustmentMapper = stockAdjustmentMapper;
        this.stockAdjustmentRepository = stockAdjustmentRepository;

        this.purchaseReturnRepository = purchaseReturnRepository;
        this.auditUtil = auditUtil;
        this.stockReportRepository = stockReportRepository;
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
    public List<StockAdjustmentResponseDto> findByCodeAndDate(Integer productCode, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59, 999_999_999);
        AddPurchase addPurchases = purchaseRepository.findTopByProduct_ProductCodeAndStatusNotAndCreatedAtBetweenOrderByCreatedAtDesc(productCode,3,startOfDay, endOfDay);
        StockReport stockReport = stockReportRepository.findTopByProduct_ProductCodeOrderByCreatedAtDesc(productCode);
        StockAdjustment  stockAdjustment = stockAdjustmentRepository.findTopByProduct_ProductCodeOrderByCreatedAtDesc(productCode);
        PurchaseReturn purchaseReturn = purchaseReturnRepository.findTopByProduct_ProductCodeAndStatusNotAndCreatedAtBetweenOrderByCreatedAtDesc(productCode,3,startOfDay, endOfDay);

        StockAdjustmentResponseDto data = stockAdjustmentMapper.toDTO(addPurchases,purchaseReturn, stockReport,stockAdjustment);
        return  List.of(data);
    }

    @Override
    public StockAdjustmentResponseDto save(StockAdjustmentRequestDto dto) {
        auditUtil.createAudit(dto);
        Product product = productRepository.findByProductCode(dto.getProductCode())
                .orElseThrow(() -> new CustomException(Constants.PRODUCT_NOT_FOUND));
        StockAdjustment stockAdjustment = stockAdjustmentMapper.toEntity(dto,product);
        StockAdjustment save = stockAdjustmentRepository.save(stockAdjustment);
        return stockAdjustmentMapper.toDTO(save);
    }


}
