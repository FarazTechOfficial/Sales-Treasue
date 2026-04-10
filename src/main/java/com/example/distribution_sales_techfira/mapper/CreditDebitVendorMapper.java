package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.CreditDebitVendorReqDTO;
import com.example.distribution_sales_techfira.dto.CreditDebitVendorResDTO;
import com.example.distribution_sales_techfira.entity.CreditDebitVendor;
import com.example.distribution_sales_techfira.entity.Note;
import com.example.distribution_sales_techfira.entity.Vendor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreditDebitVendorMapper {

    public CreditDebitVendor toEntity(CreditDebitVendorReqDTO dto, Vendor vendor, Note creditNote, Note debitNote) {
        if (dto == null) return null;

        CreditDebitVendor entity = new CreditDebitVendor();
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setTransactionType(dto.getTransactionType());
        entity.setDescription(dto.getDescription());
        entity.setTransactionDate(dto.getTransactionDate());
        entity.setAmount(dto.getAmount());
        entity.setStatus(2);
        entity.setVendor(vendor);
        entity.setCreditNote(creditNote);
        entity.setDebitNote(debitNote);
        return entity;
    }

    public CreditDebitVendorResDTO toDTO(CreditDebitVendor entity) {
        if (entity == null) return null;

        CreditDebitVendorResDTO dto = new CreditDebitVendorResDTO();
        dto.setId(entity.getId());
        dto.setTransactionType(entity.getTransactionType());
        dto.setTransactionDate(entity.getTransactionDate());
        dto.setDescription(entity.getDescription());
        dto.setAmount(entity.getAmount());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());


        if (entity.getVendor() != null) {
            dto.setVendor(entity.getVendor());
        }

        if (entity.getCreditNote() != null) {
            dto.setCreditNote(entity.getCreditNote());
        }

        if (entity.getDebitNote() != null) {
            dto.setDebitNote(entity.getDebitNote());
        }

        return dto;
    }

    public List<CreditDebitVendorResDTO> toListDTO(List<CreditDebitVendor> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
