package com.example.distribution_sales_techfira.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "licencing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class License extends BaseEntity{


    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;
    private LocalDate validTo;
    private LocalDate ValidFrom;
    private Integer numUsers;

}
