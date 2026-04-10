package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.MenuResDTO;
import com.example.distribution_sales_techfira.dto.PrivilegeReqDTO;
import com.example.distribution_sales_techfira.dto.PrivilegeResDTO;
import com.example.distribution_sales_techfira.entity.Privilege;
import com.example.distribution_sales_techfira.service.PrivilegeService;
import org.apache.poi.ss.usermodel.IconMultiStateFormatting;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrivilegeMapper {

    public Privilege toEntity(PrivilegeReqDTO dto) {
        if (dto == null) {
            return null;
        }
        Privilege entity = new Privilege();
        entity.setId(dto.getId());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        // Do not set menu here directly — handle separately
        return entity;
    }

    public PrivilegeResDTO toDTO(Privilege entity) {
        if (entity == null) {
            return null;
        }
        PrivilegeResDTO dto = new PrivilegeResDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        MenuResDTO menuDTO = MenuMapper.toDTO(entity.getMenu());
        dto.setMenu(menuDTO);
        return dto;
    }

    public List<PrivilegeResDTO> toListDTO(List<Privilege> entityList) {
        if (entityList == null) {
            return new ArrayList<>();
        }
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
