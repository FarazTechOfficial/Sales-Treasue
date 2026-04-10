package com.example.distribution_sales_techfira.service;


import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.entity.CreditDebitVendor;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;


public interface CreditDebitVendorService extends BaseService<CreditDebitVendorReqDTO,CreditDebitVendorResDTO,Integer>{
    String delete(Integer id);
    CustomPageResponse<CreditDebitVendorResDTO> getAllCreditDebitVendors(int page, int size);
    CustomPageResponse<CreditDebitVendorResDTO> getAllDebitVendors(int page,int size);
    public void importVendorsFromExcel(MultipartFile file) throws IOException;

}