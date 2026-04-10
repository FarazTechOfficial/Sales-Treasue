package com.example.distribution_sales_techfira.repository;


import com.example.distribution_sales_techfira.entity.Bank;
import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.entity.CreditDebitVendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditDebitVendorRepository extends JpaRepository<CreditDebitVendor,Integer> {

    Page<CreditDebitVendor> findByStatusNot(Integer status, Pageable pageable);

    @Query("SELECT c FROM CreditDebitVendor c WHERE c.status !=3 AND c.transactionType = 'debit'")
    Page<CreditDebitVendor> findAllActiveDebitTransactions(Pageable pageable);

    @Query("SELECT c FROM CreditDebitVendor c WHERE c.status !=3 AND c.transactionType = 'credit'")
    Page<CreditDebitVendor> findAllActiveCreditTransactions(Pageable pageable);
    List<CreditDebitVendor> findByCreatedBy(Integer userId);
    List<CreditDebitVendor> findByUpdatedBy(Integer userId);
}
