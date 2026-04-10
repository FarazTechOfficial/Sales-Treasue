package com.example.distribution_sales_techfira.repository;


import com.example.distribution_sales_techfira.entity.Branch;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

    List<Branch> findByStatusNot(Integer status);
    Page<Branch> findByStatusNot(Integer status, Pageable pageable);

    Optional<Branch> findByIdAndStatusNot(Integer id, Integer status);
    List<Branch> findByCreatedBy(Integer userId);
    List<Branch> findByUpdatedBy(Integer userId);
    List<Branch> findByCompanyId(Integer companyId);
}