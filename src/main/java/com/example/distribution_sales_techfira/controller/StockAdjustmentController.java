package com.example.distribution_sales_techfira.controller;
<<<<<<< Updated upstream
import com.example.distribution_sales_techfira.dto.StockAdjustmentRequestDto;
import com.example.distribution_sales_techfira.dto.StockAdjustmentResponseDto;
=======

import com.example.distribution_sales_techfira.dto.StockAdjustmentRequestDto;
import com.example.distribution_sales_techfira.dto.StockAdjustmentResponseDto;
import com.example.distribution_sales_techfira.dto.StockReportRequestDto;
>>>>>>> Stashed changes
import com.example.distribution_sales_techfira.dto.StockReportResponseDto;
import com.example.distribution_sales_techfira.service.BaseService;
import com.example.distribution_sales_techfira.service.impl.StockAdjustmentServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stock-adjustment")
public class StockAdjustmentController extends BaseController<StockAdjustmentRequestDto, StockAdjustmentResponseDto,Integer>{
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    private final StockAdjustmentServiceImpl stockAdjustmentService;

    protected StockAdjustmentController(BaseService<StockAdjustmentRequestDto, StockAdjustmentResponseDto, Integer> service, StockAdjustmentServiceImpl stockAdjustmentService) {
        super(service);

        this.stockAdjustmentService = stockAdjustmentService;
    }
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    @GetMapping("/by-code-and-date")
    public ResponseEntity<List<StockAdjustmentResponseDto>> getByCodeAndDate(
            @RequestParam("productCode") Integer productCode,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<StockAdjustmentResponseDto> result = stockAdjustmentService.findByCodeAndDate(productCode, date);
        return ResponseEntity.ok(result);
    }
    @GetMapping
    public ResponseEntity<List<StockReportResponseDto>> getAll( ) {
        List<StockReportResponseDto> result = stockAdjustmentService.findAllProductCode();
        return ResponseEntity.ok(result);
    }
<<<<<<< Updated upstream
=======


>>>>>>> Stashed changes
    @Override
    protected String getEntityName() {
        return "stock-adjustment";
    }
}
