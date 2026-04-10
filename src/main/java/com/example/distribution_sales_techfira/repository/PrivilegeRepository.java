package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.Menu;
import com.example.distribution_sales_techfira.entity.Privilege;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

    @Query("SELECT p FROM Privilege p WHERE p.status != 3")
    List<Privilege> findAllActivePrivileges();

    @Query("SELECT p FROM Privilege p WHERE p.status != 3")
    Page<Privilege> findAllActivePages(Pageable pageable);

    @Query("SELECT p FROM Privilege p WHERE p.status != 3 AND p.name = :name")
    Privilege findByName(@PathParam("name") String name);

    Optional<Privilege> findByMenu(Menu menu);

}
