package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.Bank;
import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank,Integer> {

    List<Bank> findByStatusNot(Integer status);
    Page<Bank> findByStatusNot(Integer status, Pageable pageable);
    Optional<Bank> findByBankName(String bankName);
    boolean existsByBankName(String bankName);
    Optional<Bank> findByBankNameIgnoreCase(String text);
    List<Bank> findByCreatedBy(Integer userId);
    List<Bank> findByUpdatedBy(Integer userId);
    List<Bank> findByStatus(Integer status);
}
