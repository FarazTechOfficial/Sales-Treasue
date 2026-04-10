package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.PrivilegeResDTO;
import com.example.distribution_sales_techfira.dto.RoleReqDTO;
import com.example.distribution_sales_techfira.dto.RoleResDTO;
import com.example.distribution_sales_techfira.entity.Role;
import java.util.List;
import com.example.distribution_sales_techfira.dto.RoleUpdateDTO;
import com.example.distribution_sales_techfira.entity.Privilege;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public static RoleResDTO toDTO(Role role) {
        if (role == null) return null;

        RoleResDTO dto = new RoleResDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setStatus(role.getStatus());
        if (role.getPrivileges() != null) {
            List<PrivilegeResDTO> privilegeDTOs = role.getPrivileges().stream()
                    .map(RoleMapper::toPrivilegeDTO)
                    .collect(Collectors.toList());
            dto.setPrivileges(privilegeDTOs);
        }

        return dto;
    }

    public static List<RoleResDTO> toListDTO(List<Role> roles) {
        return roles.stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Role toEntity(RoleReqDTO dto, Privilege privilege) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setCreatedBy(dto.getCreatedBy());
        role.setUpdatedBy(role.getUpdatedBy());
        role.setStatus(dto.getStatus());
        role.setPrivileges(List.of(privilege));
        return role;
    }

    public static void updateEntityFromDTO(Role role, RoleUpdateDTO dto) {
        role.setName(dto.getName());
    }

    private static PrivilegeResDTO toPrivilegeDTO(Privilege privilege) {
        if (privilege == null) return null;
        PrivilegeResDTO dto = new PrivilegeResDTO();
        dto.setId(privilege.getId());
        dto.setName(privilege.getName());
        dto.setStatus(privilege.getStatus());
        return dto;
    }
}
