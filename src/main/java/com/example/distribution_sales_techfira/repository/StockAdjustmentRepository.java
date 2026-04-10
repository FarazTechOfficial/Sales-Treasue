package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.StockAdjustment;
import com.example.distribution_sales_techfira.entity.StockReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment,Integer> {

    StockAdjustment findTopByProduct_ProductCodeOrderByCreatedAtDesc(Integer productCode);

}
