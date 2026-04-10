package com.example.distribution_sales_techfira.controller;


import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController<CompanyReqDTO,CompanyResDTO,Integer>{

    private final CompanyService companyService;
    public CompanyController(CompanyService companyService, CompanyService companyService1) {
        super(companyService);
        this.companyService = companyService1;
    }

    @Override
    protected String getEntityName() {
        return "company";
    }

    @GetMapping
    public ResponseEntity<BaseResponse<CustomPageResponse<CompanyResDTO>>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int zeroBasedPage = Math.max(page - 1, 0);
        System.out.println("temp-faraz testing");
        CustomPageResponse<CompanyResDTO> result = companyService.findAllPaged(zeroBasedPage, size);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }

    @GetMapping("/companies-without-pagination")
    public ResponseEntity<BaseResponse<List<CompanyResDTO>>> getAllCompaniesWithoutPagination(){
        List<CompanyResDTO> all = companyService.findAll();
        return  ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(),all)
        );
    }
}
