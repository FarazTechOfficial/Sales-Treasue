package com.example.distribution_sales_techfira.repository;

<<<<<<< Updated upstream
import com.example.distribution_sales_techfira.entity.Product;
=======
>>>>>>> Stashed changes
import com.example.distribution_sales_techfira.entity.AddPurchase;
import com.example.distribution_sales_techfira.entity.PurchaseReturn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< Updated upstream
import java.util.List;
=======
>>>>>>> Stashed changes
import java.time.LocalDateTime;
import java.util.Optional;

public interface PurchaseReturnRepository extends JpaRepository<PurchaseReturn, Integer> {

    Optional<PurchaseReturn> findTopByOrderByCreatedAtDesc();
<<<<<<< Updated upstream
    Page<PurchaseReturn> findByStatusNot(Integer status, Pageable pageable);
=======
>>>>>>> Stashed changes
    PurchaseReturn findTopByProduct_ProductCodeAndStatusNotAndCreatedAtBetweenOrderByCreatedAtDesc(
            Integer productCode,
            Integer status,
            LocalDateTime start,
            LocalDateTime end
    );
}
