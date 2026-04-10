package com.example.distribution_sales_techfira.controller;


import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.service.MenuService;
import com.example.distribution_sales_techfira.service.PrivilegeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/privilege")
public class PrivilegeController extends BaseController<PrivilegeReqDTO,PrivilegeResDTO,Integer>{

    private final PrivilegeService privilegeService;

    public PrivilegeController(PrivilegeService privilegeService) {
        super(privilegeService);
        this.privilegeService = privilegeService;
    }


    @GetMapping()
    public ResponseEntity<BaseResponse<CustomPageResponse<PrivilegeResDTO>>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        int zeroBasedPage = Math.max(page - 1, 0);
        CustomPageResponse<PrivilegeResDTO> result = privilegeService.getAllPrivilegeWithPagination(zeroBasedPage, size);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }




    @Override
    protected String getEntityName() {
        return "Privilege";
    }
}
