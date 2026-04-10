package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.MenuReqDTO;
import com.example.distribution_sales_techfira.dto.MenuResDTO;

import java.util.List;

public interface MenuService extends BaseService<MenuReqDTO,MenuResDTO,Integer> {

    MenuResDTO getSingleMenu(Integer id);
    List<MenuResDTO> getAllMenusWithoutPagination();
    CustomPageResponse<MenuResDTO> getAllMenusWithPagination(int page,int size);
}
