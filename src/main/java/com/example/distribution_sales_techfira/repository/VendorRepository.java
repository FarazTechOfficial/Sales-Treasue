package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.Bank;
import com.example.distribution_sales_techfira.entity.Branch;
import com.example.distribution_sales_techfira.entity.Note;
import com.example.distribution_sales_techfira.entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Integer> {


    @EntityGraph(attributePaths = {"bank"})
    List<Vendor> findByStatusNot(Integer status);
    Page<Vendor> findByStatusNot(Integer status, Pageable pageable);
    Optional<Vendor> findByVendorNameIgnoreCaseAndStatusNot(String vendorName,Integer status);
    List<Vendor> findByCreatedBy(Integer userId);
    List<Vendor> findByUpdatedBy(Integer userId);
    List<Vendor> findByStatus(Integer status);
}
