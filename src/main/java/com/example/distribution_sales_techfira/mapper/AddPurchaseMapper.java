package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.AddPurchaseRequestDto;
import com.example.distribution_sales_techfira.dto.AddPurchaseResponseDto;
import com.example.distribution_sales_techfira.entity.AddPurchase;
import com.example.distribution_sales_techfira.entity.Product;
import com.example.distribution_sales_techfira.util.Constants;
import org.springframework.stereotype.Component;

@Component
public class AddPurchaseMapper {

    public AddPurchase toEntity(AddPurchaseRequestDto dto, Product product) {
        AddPurchase purchase = new AddPurchase();
        purchase.setProduct(product);
        purchase.setQuantityInCarton(dto.getQuantityInCarton());
        purchase.setQunatityInPieces(dto.getQuantityInPieces());
        purchase.setUnitOfMeasurement(dto.getUnitOfMeasurement());
        purchase.setDate(dto.getDate());
        purchase.setCreatedBy(dto.getCreatedBy());
        purchase.setUpdatedBy(dto.getUpdatedBy());
        return purchase;
    }

    public AddPurchaseResponseDto toDto(AddPurchase entity) {
        AddPurchaseResponseDto dto = new AddPurchaseResponseDto();
        dto.setId(entity.getId());
        dto.setPurchaseNumber(entity.getPurchaseNumber());
        if (entity.getProduct() != null) {
            dto.setProductCode(entity.getProduct().getProductCode());
            dto.setProductDescription(entity.getProduct().getProductDescription());
        } else {
            dto.setProductCode(null);
            dto.setProductDescription(Constants.PRODUCT_NOT_FOUND);
        }
        dto.setQuantityInCarton(entity.getQuantityInCarton());
        dto.setQuantityInPieces(entity.getQunatityInPieces());
        dto.setUnitOfMeasurement(entity.getUnitOfMeasurement());
        dto.setDate(entity.getDate());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        return dto;
    }

}
