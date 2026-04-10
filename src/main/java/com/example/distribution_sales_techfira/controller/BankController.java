package com.example.distribution_sales_techfira.controller;

import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController extends BaseController<BankReqDTO, BankResDTO, Integer> {

    private final BankService bankService;
    public BankController(BankService bankService, BankService bankService1) {
        super(bankService);
        this.bankService = bankService1;
    }
    @Override
    protected String getEntityName() {
        return "bank";
    }
    @GetMapping
    public ResponseEntity<BaseResponse<List<BankResDTO>>> getAll() {
        List<BankResDTO> result = bankService.findAll();
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }
    @GetMapping("/banks")
    public ResponseEntity<BaseResponse<CustomPageResponse<BankResDTO>>> getAllWithPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int zeroBasedPage = Math.max(page - 1, 0);
        CustomPageResponse<BankResDTO> result = bankService.findAllPaged(zeroBasedPage, size);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }
}
