package com.example.distribution_sales_techfira.service;


import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.PrivilegeReqDTO;
import com.example.distribution_sales_techfira.dto.PrivilegeResDTO;

import java.util.List;

public interface PrivilegeService extends BaseService<PrivilegeReqDTO, PrivilegeResDTO,Integer> {

    List<PrivilegeResDTO> getAllPrivilegeWithoutPagination();
    CustomPageResponse<PrivilegeResDTO> getAllPrivilegeWithPagination(int page, int size);

}
