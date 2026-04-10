package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.entity.JournalVouchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JournalVoucherRepository extends JpaRepository<JournalVouchers, Integer> {
   // void deleteByJournalVoucherId(Long id);
   @Query("SELECT jv FROM JournalVouchers jv WHERE jv.status != 3")
   List<JournalVouchers> findAllActive();
    @Query("SELECT jv FROM JournalVouchers jv WHERE jv.status != 3")
    Page<JournalVouchers> findAllActive(Pageable pageable);
    Optional<JournalVouchers> findByIdAndStatusNot(Integer id, Integer status);

    List<JournalVouchers> findByStatusNot(Integer status);
    Page<JournalVouchers> findByStatusNot(Integer status, Pageable pageable);
    List<JournalVouchers> findByCreatedBy(Integer userId);
    List<JournalVouchers> findByUpdatedBy(Integer userId);

}
