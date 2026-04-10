package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BaseService<T, R, ID> {

    default R save(T requestDTO) {
        throw new UnsupportedOperationException("save not supported");
    }

    default List<R> findAll() {
        throw new UnsupportedOperationException("findAll not supported");
    }

    default CustomPageResponse<R> findAllPaged(int page, int size) {
        throw new UnsupportedOperationException("findAllPaged not supported");
    }

    default R findByID(ID id) {
        throw new UnsupportedOperationException("findByID not supported");
    }

    default R update(ID id, T requestDTO) {
        throw new UnsupportedOperationException("update not supported");
    }

    default void softDeleteById(ID id) {
        throw new UnsupportedOperationException("softDeleteById not supported");
    }

    default void updateStatus(ID id, Integer status) {
        throw new UnsupportedOperationException("updateStatus not supported");
    }

    default List<R> findAllBanksCreatedBy(Integer userId) {
        throw new UnsupportedOperationException("findAllBanksCreatedBy not supported");
    }

    default List<R> findAllBanksUpdatedBy(Integer userId) {
        throw new UnsupportedOperationException("findAllBanksUpdatedBy not supported");
    }
}

