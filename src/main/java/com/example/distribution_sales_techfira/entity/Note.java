package com.example.distribution_sales_techfira.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Note extends BaseEntity{

    private String code;

    private String noteName;

    private String noteType;

   // private Long noteAmount;



}
