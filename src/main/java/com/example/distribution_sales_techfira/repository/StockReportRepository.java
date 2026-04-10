package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.AddPurchase;
import com.example.distribution_sales_techfira.entity.PurchaseReturn;
import com.example.distribution_sales_techfira.entity.StockReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface StockReportRepository extends JpaRepository<StockReport,Integer> {

    StockReport findTopByProduct_ProductCodeOrderByCreatedAtDesc(Integer productCode);

}
