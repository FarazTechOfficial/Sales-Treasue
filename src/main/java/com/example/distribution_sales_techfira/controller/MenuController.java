package com.example.distribution_sales_techfira.controller;

import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController<MenuReqDTO,MenuResDTO,Integer>{

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        super(menuService);
        this.menuService = menuService;
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse<CustomPageResponse<MenuResDTO>>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        int zeroBasedPage = Math.max(page - 1, 0);
        CustomPageResponse<MenuResDTO> result = menuService.getAllMenusWithPagination(zeroBasedPage, size);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }

    @Override
    protected String getEntityName() {
        return "menu";
    }
}
