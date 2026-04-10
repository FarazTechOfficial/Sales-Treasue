package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.StockAdjustmentRequestDto;
import com.example.distribution_sales_techfira.dto.StockAdjustmentResponseDto;
import com.example.distribution_sales_techfira.dto.StockReportRequestDto;
import com.example.distribution_sales_techfira.dto.StockReportResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface StockAdjustmentService extends BaseService<StockAdjustmentRequestDto, StockAdjustmentResponseDto,Integer>{
    public List<StockReportResponseDto> findAllProductCode();
    public List<StockAdjustmentResponseDto> findByCodeAndDate(Integer productCode, LocalDate date);
    public StockAdjustmentResponseDto save(StockAdjustmentRequestDto dto);
}
