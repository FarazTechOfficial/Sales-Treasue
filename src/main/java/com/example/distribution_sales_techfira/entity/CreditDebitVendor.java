package com.example.distribution_sales_techfira.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CreditDebitVendor extends BaseEntity{


    @ManyToOne(fetch = FetchType.EAGER)
    private Vendor vendor;
    private LocalDate transactionDate;
    private String transactionType;

    private Float amount;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Note creditNote;

    @ManyToOne(fetch = FetchType.EAGER)
    private Note debitNote;



//    @CreationTimestamp
//    private LocalDate createdAt;
//    @UpdateTimestamp
//    private LocalDate updatedAt;
    private LocalDate DeletedAt;



}
