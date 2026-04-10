package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.Bank;
import com.example.distribution_sales_techfira.entity.Branch;
import com.example.distribution_sales_techfira.entity.License;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable; // ✅ correct one

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LicenceRepository extends JpaRepository<License, Integer> {

    List<License> findByStatusNot(Integer status);
    Page<License> findByStatusNot(Integer status, Pageable pageable);
    Optional<License> findByIdAndStatusNot(Integer id, int status);
    Optional<License> findByCompanyId(Integer companyId);
    List<License> findByCreatedBy(Integer userId);
    List<License> findByUpdatedBy(Integer userId);
}
