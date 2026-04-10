package com.example.distribution_sales_techfira.controller;

import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.PurchaseReturnReqDTO;
import com.example.distribution_sales_techfira.dto.PurchaseReturnResDTO;
import com.example.distribution_sales_techfira.service.BaseService;
import com.example.distribution_sales_techfira.service.PurchaseReturnService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-return")
public class PurchaseReturnController extends BaseController<PurchaseReturnReqDTO, PurchaseReturnResDTO, Integer>{

    private final PurchaseReturnService purchaseReturnService;

    public PurchaseReturnController(BaseService<PurchaseReturnReqDTO, PurchaseReturnResDTO, Integer> service, PurchaseReturnService purchaseReturnService) {
        super(service);
        this.purchaseReturnService = purchaseReturnService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<PurchaseReturnResDTO>> getAllPurchaseReturns() {
        List<PurchaseReturnResDTO> returns = purchaseReturnService.findAll();
        return ResponseEntity.ok(returns);
    }

    @GetMapping("/purchase_returns")
    public ResponseEntity<BaseResponse<CustomPageResponse<PurchaseReturnResDTO>>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {


        CustomPageResponse<PurchaseReturnResDTO> result = purchaseReturnService.findAllPaged(page, size);

        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));

    }


    @Override
    protected String getEntityName() {
        return "PurchaseReturn";
    }
}
