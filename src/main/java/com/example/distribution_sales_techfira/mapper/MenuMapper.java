package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.MenuReqDTO;
import com.example.distribution_sales_techfira.dto.MenuResDTO;
import com.example.distribution_sales_techfira.entity.Menu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuMapper {

    private MenuMapper() {} // private constructor for utility class

    public static Menu toEntity(MenuReqDTO dto) {
        if (dto == null) return null;

        Menu menu = new Menu();
        menu.setCreatedBy(dto.getCreatedBy() );
        menu.setUpdatedBy(dto.getUpdatedBy() );
        menu.setName(dto.getName());
        menu.setPath(dto.getPath());
        menu.setStatus(dto.getStatus());
        return menu;
    }

    public static MenuResDTO toDTO(Menu entity) {
        if (entity == null) return null;

        MenuResDTO dto = new MenuResDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPath(entity.getPath());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static List<MenuResDTO> toListDTO(List<Menu> entities) {
        if (entities == null) return null;

        List<MenuResDTO> dtoList = new ArrayList<>();
        for (Menu menu : entities) {
            dtoList.add(toDTO(menu));
        }
        return dtoList;
    }
}
