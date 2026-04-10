package com.example.distribution_sales_techfira.controller;

import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.AddPurchaseRequestDto;
import com.example.distribution_sales_techfira.dto.AddPurchaseResponseDto;
import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.service.AddPurchaseService;
import com.example.distribution_sales_techfira.service.BaseService;
import com.example.distribution_sales_techfira.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/add-purchase")
public class AddPurchaseController  extends BaseController<AddPurchaseRequestDto, AddPurchaseResponseDto,Integer>{
    private final AddPurchaseService purchaseService;

    public AddPurchaseController(BaseService<AddPurchaseRequestDto, AddPurchaseResponseDto, Integer> service, AddPurchaseService purchaseService) {
        super(service);
        this.purchaseService = purchaseService;
    }

    @Override
    protected String getEntityName() {
        return "AddPurchase";
    }

    @GetMapping("/without-pagination")
    public ResponseEntity<BaseResponse<List<AddPurchaseResponseDto>>> getAll(){
        List<AddPurchaseResponseDto> purchases = purchaseService.findAll();
        return ResponseEntity.ok(new BaseResponse<>(Constants.SUCCESS_MESSAGE, HttpStatus.OK.value(),purchases));

    }

    @GetMapping("/paged")
    public ResponseEntity<BaseResponse<CustomPageResponse<AddPurchaseResponseDto>>> getPagedPurchases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        CustomPageResponse<AddPurchaseResponseDto> pagedResponse = purchaseService.findAllPaged(page, size);
        return ResponseEntity.ok(new BaseResponse<>(Constants.SUCCESS_MESSAGE, HttpStatus.OK.value(), pagedResponse));
    }
}
