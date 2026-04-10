package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.RoleReqDTO;
import com.example.distribution_sales_techfira.dto.RoleResDTO;
import com.example.distribution_sales_techfira.dto.RoleUpdateDTO;

import java.util.List;

public interface RoleService {

    RoleResDTO save(RoleReqDTO roleReqDTO);
    RoleResDTO getSinglePrivilege(Integer id);
    List<RoleResDTO> getAllRoleWithoutPagination();
    CustomPageResponse<RoleResDTO> getAllRoleWithPagination(int page, int size);
    RoleResDTO update(Integer id,RoleReqDTO roleReqDTO);
    RoleResDTO updateStatus(Integer id, Integer status);
    void  delete(Integer id);
}