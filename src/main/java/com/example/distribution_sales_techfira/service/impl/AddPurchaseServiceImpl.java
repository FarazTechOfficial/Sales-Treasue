package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.AddPurchaseRequestDto;
import com.example.distribution_sales_techfira.dto.AddPurchaseResponseDto;
import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.entity.AddPurchase;
import com.example.distribution_sales_techfira.entity.Product;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.AddPurchaseMapper;
import com.example.distribution_sales_techfira.repository.ProductRepository;
import com.example.distribution_sales_techfira.repository.PurchaseRepository;
import com.example.distribution_sales_techfira.service.AddPurchaseService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import com.example.distribution_sales_techfira.util.Constants;
import com.example.distribution_sales_techfira.util.PurchaeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AddPurchaseServiceImpl implements AddPurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final AddPurchaseMapper purchaseMapper;
    private final AuditUtil auditUtil;
    private final ProductRepository productRepository;

    public AddPurchaseServiceImpl(PurchaseRepository purchaseRepository, AddPurchaseMapper purchaseMapper, AuditUtil auditUtil,ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseMapper = purchaseMapper;
        this.auditUtil = auditUtil;
        this.productRepository=productRepository;

    }

    @Override
    public AddPurchaseResponseDto save(AddPurchaseRequestDto dto) {
        auditUtil.createAudit(dto);

        Product product = productRepository.findByProductCode(dto.getProductCode())
                .orElseThrow(() -> new CustomException(Constants.PRODUCT_NOT_FOUND));

        AddPurchase purchase = purchaseMapper.toEntity(dto, product);
        purchase.setPurchaseNumber(PurchaeUtil.generatePurchaseNumber());
        AddPurchase saved = purchaseRepository.save(purchase);
        return purchaseMapper.toDto(saved);
    }


    @Override
    public AddPurchaseResponseDto findByID(Integer id){
        AddPurchase purchase = purchaseRepository.findById(id).orElseThrow(() -> new CustomException(Constants.PRODUCT_NOT_FOUND));
        return purchaseMapper.toDto(purchase);
}

@Override
    public List<AddPurchaseResponseDto> findAll(){
        return purchaseRepository.findAll().stream().map(purchaseMapper::toDto).collect(Collectors.toList());
}

    @Override
    public CustomPageResponse<AddPurchaseResponseDto> findAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<AddPurchase> pagedResult = purchaseRepository.findAll(pageable);

        List<AddPurchaseResponseDto> content = pagedResult.getContent()
                .stream()
                .map(purchaseMapper::toDto)
                .collect(Collectors.toList());

        return new CustomPageResponse<>(
                content,
                pagedResult.getSort().isUnsorted(),
                pagedResult.getSort().isSorted(),
                pagedResult.getTotalElements(),
                size,
                page + 1
        );
    }

}
