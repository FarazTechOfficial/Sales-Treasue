package com.example.distribution_sales_techfira.controller;

import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.entity.BaseEntity;
import com.example.distribution_sales_techfira.entity.Role;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController{

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;

    }

    @PostMapping()
    public ResponseEntity<BaseResponse<RoleResDTO>> create(@Valid @RequestBody RoleReqDTO roleReqDTO) {
        RoleResDTO result = roleService.save(roleReqDTO);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }


    @GetMapping()
    public ResponseEntity<BaseResponse<CustomPageResponse<RoleResDTO>>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        int zeroBasedPage = Math.max(page - 1, 0);
        CustomPageResponse<RoleResDTO> result = roleService.getAllRoleWithPagination(zeroBasedPage, size);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<RoleResDTO>> getRoleById(@PathVariable Integer id) {
        RoleResDTO result = roleService.getSinglePrivilege(id);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable Integer id) {
        roleService.delete(id);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), "Deleted deleted: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<RoleResDTO>> update(@PathVariable Integer id, @RequestBody RoleReqDTO roleReqDTO) {
        RoleResDTO result = roleService.update(id, roleReqDTO);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody StatusDTO statusDTO) {
        roleService.updateStatus(id, statusDTO.getStatus());
        return ResponseEntity.ok().build();
    }
}