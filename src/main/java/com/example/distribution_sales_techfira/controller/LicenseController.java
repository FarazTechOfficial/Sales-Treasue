package com.example.distribution_sales_techfira.controller;


import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.service.CompanyService;
import com.example.distribution_sales_techfira.service.LicenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/license")
public class LicenseController extends BaseController<LicenseReqDTO,LicenseResDTO,Integer>{

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        super(licenseService);
        this.licenseService = licenseService;
    }
    @Override
    protected String getEntityName() {
        return "license";
    }

    @GetMapping
    public ResponseEntity<BaseResponse<CustomPageResponse<LicenseResDTO>>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        int zeroBasedPage = Math.max(page - 1, 0);
        CustomPageResponse<LicenseResDTO> result = licenseService.findAllPaged(zeroBasedPage, size);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }
}

