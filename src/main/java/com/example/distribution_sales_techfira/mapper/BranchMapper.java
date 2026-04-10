package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.BranchReqDTO;
import com.example.distribution_sales_techfira.dto.BranchResDTO;
import com.example.distribution_sales_techfira.entity.Branch;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class BranchMapper {

    public static Branch toEntity(BranchReqDTO dto) {
        if (dto == null) return null;

        Branch branch = new Branch();
        branch.setCreatedBy(dto.getCreatedBy());
        branch.setUpdatedBy(dto.getUpdatedBy());
        branch.setName(dto.getName());
        branch.setEmail(dto.getEmail());
        branch.setNumber(dto.getNumber());
        branch.setStatus(dto.getStatus() != null ? dto.getStatus() : 2);
        branch.setCompany(dto.getCompany());
        return branch;
    }

    public static BranchResDTO toDTO(Branch branch) {
        if (branch == null) return null;

        BranchResDTO dto = new BranchResDTO();

        dto.setId(branch.getId());
        dto.setName(branch.getName());
        dto.setEmail(branch.getEmail());
        dto.setNumber(branch.getNumber());
        dto.setStatus(branch.getStatus());
        dto.setCompany(branch.getCompany());
        dto.setCreatedAt(branch.getCreatedAt());
        dto.setCreatedBy(branch.getCreatedBy());
        dto.setUpdatedAt(branch.getUpdatedAt());
        dto.setUpdatedBy(branch.getUpdatedBy());
        return dto;

    }

    public static List<BranchResDTO> toDTOList(List<Branch> branches) {
        return branches.stream()
                .map(BranchMapper::toDTO)
                .collect(Collectors.toList());
    }
}
