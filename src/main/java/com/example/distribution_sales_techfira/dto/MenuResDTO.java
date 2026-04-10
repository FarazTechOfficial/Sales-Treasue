package com.example.distribution_sales_techfira.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuResDTO {
    private Integer id;
    private String name;
    private String path;
    private Integer status;

}
