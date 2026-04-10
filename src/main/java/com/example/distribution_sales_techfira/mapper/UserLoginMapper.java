package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.MenuResDTO;
import com.example.distribution_sales_techfira.dto.PrivilegeResDTO;
import com.example.distribution_sales_techfira.dto.RoleResDTO;
import com.example.distribution_sales_techfira.dto.UserLoginDTO;
import com.example.distribution_sales_techfira.entity.Menu;
import com.example.distribution_sales_techfira.entity.Privilege;
import com.example.distribution_sales_techfira.entity.Role;
import com.example.distribution_sales_techfira.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserLoginMapper {

    public static UserLoginDTO toUserLoginDTO(User user) {
        if (user == null) return null;

        UserLoginDTO dto = new UserLoginDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());

        if (user.getRole() != null) {
            dto.setRole(toRoleResDTO(user.getRole()));
        }

        return dto;
    }

    private static RoleResDTO toRoleResDTO(Role role) {
        RoleResDTO dto = new RoleResDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());

        if (role.getPrivileges() != null) {
            List<PrivilegeResDTO> privilegeDTOs = role.getPrivileges().stream()
                    .map(UserLoginMapper::toPrivilegeResDTO)
                    .collect(Collectors.toList());
            dto.setPrivileges(privilegeDTOs);
        }

        return dto;
    }

    private static PrivilegeResDTO toPrivilegeResDTO(Privilege privilege) {
        PrivilegeResDTO dto = new PrivilegeResDTO();
        dto.setId(privilege.getId());
        dto.setName(privilege.getName());
        MenuResDTO menuDTO = MenuMapper.toDTO(privilege.getMenu());
        dto.setMenu(menuDTO);

        return dto;
    }

    private static MenuResDTO toMenuResDTO(Menu menu) {
        MenuResDTO dto = new MenuResDTO();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setPath(menu.getPath());
        return dto;
    }
}
