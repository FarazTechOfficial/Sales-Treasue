package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.PurchaseReturnReqDTO;
import com.example.distribution_sales_techfira.dto.PurchaseReturnResDTO;
import com.example.distribution_sales_techfira.entity.PurchaseReturn;
import org.springframework.stereotype.Component;

@Component
public class PurchaseReturnMapper {

    public PurchaseReturn toEntity(PurchaseReturnReqDTO dto) {

        if (dto == null) {
            return null;
        }

        PurchaseReturn purchaseReturn = new PurchaseReturn();
        purchaseReturn.setQuantityInCarton(dto.getQuantityInCarton());
        purchaseReturn.setQuantityInPieces(dto.getQuantityInPieces());
        purchaseReturn.setUnitOfMeasurement(dto.getUnitOfMeasurement());
        purchaseReturn.setDate(dto.getDate());
        purchaseReturn.setCreatedBy(dto.getCreatedBy());
        purchaseReturn.setStatus(dto.getStatus());
        return purchaseReturn;
    }


    public PurchaseReturnResDTO toDto(PurchaseReturn entity) {

        if (entity == null) {
            return null;
        }

        PurchaseReturnResDTO dto = new PurchaseReturnResDTO();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());

        dto.setPurchaseNumber(entity.getPurchaseNumber());
        dto.setQuantityInCarton(entity.getQuantityInCarton());
        dto.setQuantityInPieces(entity.getQuantityInPieces());
        dto.setUnitOfMeasurement(entity.getUnitOfMeasurement());
        dto.setStatus(entity.getStatus());
        dto.setDate(entity.getDate());


        if (entity.getProduct() != null) {
            dto.setProductCode(entity.getProduct().getProductCode());
            dto.setProductDescription(entity.getProduct().getProductDescription());
        }

        return dto;

    }
}

