package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.AddPurchaseResponseDto;
import com.example.distribution_sales_techfira.dto.ProductResDTO;
import com.example.distribution_sales_techfira.dto.StockReportRequestDto;
import com.example.distribution_sales_techfira.dto.StockReportResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface StockReportService extends BaseService<StockReportRequestDto, StockReportResponseDto,Integer>{
    public List<StockReportResponseDto> findAllProductCode();
    public List<StockReportResponseDto> findByCodeAndDate(Integer productCode, LocalDate date);
    //public StockReportResponseDto update(Integer id, StockReportRequestDto dto);
}
