package com.example.distribution_sales_techfira.controller;
<<<<<<< Updated upstream
=======

import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.JournalVoucherResDTO;
>>>>>>> Stashed changes
import com.example.distribution_sales_techfira.dto.StockReportRequestDto;
import com.example.distribution_sales_techfira.dto.StockReportResponseDto;
import com.example.distribution_sales_techfira.service.BaseService;
import com.example.distribution_sales_techfira.service.impl.StockReportServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
<<<<<<< Updated upstream
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/stock-report")
public class StockReportController extends BaseController<StockReportRequestDto, StockReportResponseDto,Integer>{
    private final StockReportServiceImpl stockReportService;
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stock-report")
public class StockReportController extends BaseController<StockReportRequestDto, StockReportResponseDto,Integer>{

    private final StockReportServiceImpl stockReportService;

>>>>>>> Stashed changes
    protected StockReportController(BaseService<StockReportRequestDto, StockReportResponseDto, Integer> service, StockReportServiceImpl stockReportService) {
        super(service);
        this.stockReportService = stockReportService;
    }
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    @GetMapping("/by-code-and-date")
    public ResponseEntity<List<StockReportResponseDto>> getByCodeAndDate(
            @RequestParam("productCode") Integer productCode,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
<<<<<<< Updated upstream
        List<StockReportResponseDto> result = stockReportService.findByCodeAndDate(productCode, date);
        return ResponseEntity.ok(result);
    }
=======

        List<StockReportResponseDto> result = stockReportService.findByCodeAndDate(productCode, date);
        return ResponseEntity.ok(result);
    }

>>>>>>> Stashed changes
    @GetMapping
    public ResponseEntity<List<StockReportResponseDto>> getAll( ) {
        List<StockReportResponseDto> result = stockReportService.findAllProductCode();
        return ResponseEntity.ok(result);
    }
    @Override
    protected String getEntityName() {
        return "stock-report";
    }
}
