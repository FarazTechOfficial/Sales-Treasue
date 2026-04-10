package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.Role;
import com.example.distribution_sales_techfira.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
   User findByEmail(String email);
   void deleteByEmail(String email);

   User findByResetToken(String token);
   List<User> findAllByStatusNot(Integer status);

   Page<User> findAllByStatusNot(int status, Pageable pageable);


}
