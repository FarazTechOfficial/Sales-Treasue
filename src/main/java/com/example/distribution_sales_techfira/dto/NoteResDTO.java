package com.example.distribution_sales_techfira.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteResDTO extends BaseDTO {
    private String code;

    private String noteName;

    private String noteType;

    //private Long noteAmount;
}