package com.example.distribution_sales_techfira.dto;


import com.example.distribution_sales_techfira.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrivilegeResDTO {

    private Integer id;
    private Integer status;
    private String name;
    private MenuResDTO menu;

}
