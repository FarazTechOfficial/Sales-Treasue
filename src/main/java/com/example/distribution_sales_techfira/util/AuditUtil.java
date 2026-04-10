package com.example.distribution_sales_techfira.util;
import com.example.distribution_sales_techfira.dto.BaseDTO;
import com.example.distribution_sales_techfira.service.impl.UserServiceImp;
import org.springframework.stereotype.Component;
@Component
public class AuditUtil {
    private final UserServiceImp userServiceImp;
    public AuditUtil(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }
    public void createAudit(BaseDTO dto) {
        Integer userId = userServiceImp.getLoggedInUserId();
        dto.setCreatedBy(userId);
        dto.setStatus(2);
    }
    public void updateAudit(BaseDTO dto) {
        Integer userId = userServiceImp.getLoggedInUserId();
        dto.setUpdatedBy(userId);

    }


}