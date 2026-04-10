package com.example.distribution_sales_techfira.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteReqDTO extends BaseDTO {

    private String code;

    private String noteName;

    private String noteType;

   // private Long noteAmount;
}
