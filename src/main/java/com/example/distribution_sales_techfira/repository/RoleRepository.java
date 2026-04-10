package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);

    @Query("SELECT r FROM  Role r WHERE r.status != 3")
    List<Role> findAllActiveRoles();

    @Query("SELECT r FROM  Role r WHERE r.status != 3")
    Page<Role> ActiveRolePages(Pageable pageable);
}