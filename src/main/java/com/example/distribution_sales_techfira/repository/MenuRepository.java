package com.example.distribution_sales_techfira.repository;


import com.example.distribution_sales_techfira.entity.Menu;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Integer> {

    @Query("SELECT m FROM Menu m WHERE m.status != 3")
    List<Menu> findAllMenu();

    @Query("SELECT m FROM Menu m WHERE m.status != 3 AND m.id = :id")
    Menu findByIdActive(@PathParam("id") Integer id);
    Menu findByName(String name);

    @Query("SELECT m FROM Menu m WHERE m.status != 3")
    Page<Menu> findAllActivePages(Pageable pageable);

}
