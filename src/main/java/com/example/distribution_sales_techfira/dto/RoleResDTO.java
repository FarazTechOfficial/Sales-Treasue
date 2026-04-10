package com.example.distribution_sales_techfira.dto;

import com.example.distribution_sales_techfira.entity.Privilege;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResDTO {
    private Integer id;
    private String name;
    private Integer status;
    private List<PrivilegeResDTO> privileges;

    // Note: Using @Getter and @Setter annotations from Lombok
    // instead of @Data to be more explicit
}