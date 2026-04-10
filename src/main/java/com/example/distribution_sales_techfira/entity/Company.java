package com.example.distribution_sales_techfira.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseEntity{


    private String name;
    private String email;
    private String phone;
    private String address;
    @JsonManagedReference
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<Branch> branches;
    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private License licence;

}
