package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.LicenseReqDTO;
import com.example.distribution_sales_techfira.dto.LicenseResDTO;
import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.entity.License;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class LicenseMapper {

    // Convert Request DTO to Entity
    public static License toEntity(LicenseReqDTO dto) {
        if (dto == null) return null;

        License license = new License();
        license.setCreatedBy(dto.getCreatedBy());
        license.setUpdatedBy(dto.getUpdatedBy());
        license.setValidFrom(dto.getValidFrom());
        license.setValidTo(dto.getValidTo());
        license.setNumUsers(dto.getNumUsers());
        license.setStatus(dto.getStatus() != null ? dto.getStatus() : 2);
        license.setCompany(dto.getCompany()); // assuming company is set externally or included in dto

        return license;
    }

    // Convert Entity to Response DTO
    public static LicenseResDTO toDTO(License license) {
        if (license == null) return null;

        LicenseResDTO dto = new LicenseResDTO();
        dto.setId(license.getId());
        dto.setValidFrom(license.getValidFrom());
        dto.setValidTo(license.getValidTo());
        dto.setNumUsers(license.getNumUsers());
        dto.setStatus(license.getStatus());
        dto.setCompany(license.getCompany());
        dto.setCreatedAt(license.getCreatedAt());
        dto.setCreatedBy(license.getCreatedBy());
        dto.setUpdatedAt(license.getUpdatedAt());
        dto.setUpdatedBy(license.getUpdatedBy());

        return dto;
    }

    // Convert list of entities to list of response DTOs
    public static List<LicenseResDTO> toDTOList(List<License> licenses) {
        return licenses.stream()
                .map(LicenseMapper::toDTO)
                .collect(Collectors.toList());
    }
}
