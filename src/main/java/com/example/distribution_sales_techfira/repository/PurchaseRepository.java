package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.AddPurchase;
import com.example.distribution_sales_techfira.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<AddPurchase,Integer > {
    List<AddPurchase> findByStatusNot(Integer status);
    AddPurchase findTopByProduct_ProductCodeAndStatusNotAndCreatedAtBetweenOrderByCreatedAtDesc(
            Integer productCode,
            Integer status,
            LocalDateTime start,
            LocalDateTime end
    );


}
