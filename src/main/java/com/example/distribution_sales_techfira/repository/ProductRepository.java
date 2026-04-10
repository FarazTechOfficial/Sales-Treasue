package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    Optional<Product> findByProductCode(Integer productCode);
    List<Product> findByStatusNot(Integer status);
    Page<Product> findByStatusNot(Integer status,Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status <> 3 AND ( CAST(p.productCode AS string) LIKE CONCAT('%', :keyword, '%') OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);

    Page<Product> findByProductCodeOrProductDescriptionContainingIgnoreCase(
            Integer productCode,String description,Pageable pageable
    );

    List<Product> findByProductCodeAndCreatedAtBetweenAndStatusNot(Integer productCode,LocalDateTime  startOfDay,LocalDateTime  endOfDay,Integer status);

}
