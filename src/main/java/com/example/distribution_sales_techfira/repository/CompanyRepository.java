package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.Bank;
import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    List<Company> findByStatusNot(Integer status);
    Page<Company> findByStatusNot(Integer status, Pageable pageable);
    Optional<Company> findByIdAndStatusNot(Integer id, int status);
    Optional<Company> findByNameIgnoreCase(String name);
    
    @Query("SELECT c FROM Company c WHERE c.createdBy = ?1 AND c.status != 3")
    List<Company> findByCreatedBy(Integer userId);

    @Query("SELECT c FROM Company c WHERE c.updatedBy = ?1 AND c.status != 3")
    List<Company> findByUpdatedBy(Integer userId);
}
