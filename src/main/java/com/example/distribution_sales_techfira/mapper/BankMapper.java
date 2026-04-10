package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.BankReqDTO;
import com.example.distribution_sales_techfira.dto.BankResDTO;
import com.example.distribution_sales_techfira.entity.Bank;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class BankMapper {

    public static Bank toEntity(BankReqDTO dto) {
        if (dto == null) return null;
        Bank bank = new Bank();
        bank.setBankName(dto.getBankName() != null ? dto.getBankName() : "");
        bank.setCountryName(dto.getCountryName() != null ? dto.getCountryName() : "");
        bank.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        bank.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        bank.setCreatedBy(dto.getCreatedBy() );
        bank.setUpdatedBy(dto.getUpdatedBy() );
        bank.setStatus(dto.getStatus());
        return bank;
    }
    public static BankResDTO toDTO(Bank entity) {
        if (entity == null) return null;

        BankResDTO dto = new BankResDTO();
        dto.setId(entity.getId());
        dto.setBankName(entity.getBankName());
        dto.setCountryName(entity.getCountryName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static List<BankResDTO> toDTOList(List<Bank> entities) {
        return entities.stream().map(BankMapper::toDTO).collect(Collectors.toList());
    }
}
