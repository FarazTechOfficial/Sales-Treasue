package com.example.distribution_sales_techfira.controller;


import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.service.BranchService;
import com.example.distribution_sales_techfira.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/branch")
public class BranchController extends BaseController<BranchReqDTO,BranchResDTO,Integer>{

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        super(branchService);
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<CustomPageResponse<BranchResDTO>>> getAllPaged(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size){
        int zeroBasedPage = Math.max(page -1,0);
        CustomPageResponse<BranchResDTO> result = branchService.findAllPaged(zeroBasedPage, size);
        result.setPage(page);
        return ResponseEntity.ok(new BaseResponse<>("success",HttpStatus.OK.value(),result));
    }

    @Override
    protected String getEntityName() {
        return "branch";
    }
}