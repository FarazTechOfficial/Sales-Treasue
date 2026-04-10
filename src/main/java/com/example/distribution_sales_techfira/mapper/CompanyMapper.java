package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.CompanyReqDTO;
import com.example.distribution_sales_techfira.dto.CompanyResDTO;
import com.example.distribution_sales_techfira.entity.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CompanyMapper {

    public static Company toEntity(CompanyReqDTO dto) {
        if (dto == null) return null;

        Company company = new Company();
        company.setCreatedBy(dto.getCreatedBy());
        company.setUpdatedBy(dto.getUpdatedBy());
        company.setName(dto.getName());
        company.setEmail(dto.getEmail());
        company.setPhone(dto.getPhone());
        company.setAddress(dto.getAddress());
        company.setStatus(dto.getStatus() != null ? dto.getStatus() : 2);
//        company.setLicence(dto.getLicence());
//        company.setBranches(dto.getBranches());
        return company;
    }

    public static CompanyResDTO toDTO(Company entity) {
        if (entity == null) return null;

        CompanyResDTO dto = new CompanyResDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setStatus(entity.getStatus());
        dto.setLicence(entity.getLicence());
        dto.setBranches(entity.getBranches());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        return dto;
    }

    public static List<CompanyResDTO> toDTOList(List<Company> entities) {
        return entities.stream()
                .map(CompanyMapper::toDTO)
                .collect(Collectors.toList());
    }
}
