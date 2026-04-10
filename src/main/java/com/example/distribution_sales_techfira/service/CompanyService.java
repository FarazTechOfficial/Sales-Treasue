package com.example.distribution_sales_techfira.service;


import com.example.distribution_sales_techfira.controller.BaseController;
import com.example.distribution_sales_techfira.dto.BranchResDTO;
import com.example.distribution_sales_techfira.dto.CompanyReqDTO;
import com.example.distribution_sales_techfira.dto.CompanyResDTO;
import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.repository.BranchRepository;
import com.example.distribution_sales_techfira.repository.CompanyRepository;
import com.example.distribution_sales_techfira.repository.LicenceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CompanyService extends BaseService<CompanyReqDTO,CompanyResDTO,Integer> {
    CompanyResDTO save(CompanyReqDTO companyReqDTO);
    CompanyResDTO findByID(Integer id);
    List<CompanyResDTO> findAll();
    void softDeleteById(Integer id);
    CompanyResDTO update(Integer id,CompanyReqDTO companyReqDTO);
    CustomPageResponse<CompanyResDTO> findAllPaged(int page, int size);

    void updateStatus(Integer id, Integer status);
}
