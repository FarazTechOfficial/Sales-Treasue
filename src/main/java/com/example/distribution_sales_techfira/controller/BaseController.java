package com.example.distribution_sales_techfira.controller;
import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.StatusDTO;
import com.example.distribution_sales_techfira.service.BaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<T, R, ID> {
    protected final BaseService<T, R, ID> service;
    protected BaseController(BaseService<T, R, ID> service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<BaseResponse<R>> create(@Valid @RequestBody T requestDTO) {
        R result = service.save(requestDTO);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<R>> getById(@PathVariable ID id) {
        R result = service.findByID(id);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<R>> update(@PathVariable ID id, @RequestBody T requestDTO) {
        R result = service.update(id, requestDTO);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable ID id) {
        service.softDeleteById(id);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(),
                "Deleted " + getEntityName() + ": " + id));
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable ID id, @RequestBody StatusDTO statusDTO) {
        service.updateStatus(id, statusDTO.getStatus());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/created-by/{id}")
    public ResponseEntity<BaseResponse<List<R>>> getAllCreatedBy(@PathVariable Integer id) {
        List<R> result = service.findAllBanksCreatedBy(id);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }
    @GetMapping("/updated-by/{id}")
    public ResponseEntity<BaseResponse<List<R>>> getAllUpdatedBy(@PathVariable Integer id) {
        List<R> result = service.findAllBanksUpdatedBy(id);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }
    protected abstract String getEntityName();
}