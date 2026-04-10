package com.example.distribution_sales_techfira.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendor extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
    @Column(nullable = false)
    private String vendorName;
    @Column(unique = true)
    private String vendorCode;  // Auto-generated e.g., VND01
    private String contactNumber;
    private String address;
    private String emailAddress;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Bank bank;
    private String accountNumber;
    private Integer status;


}
