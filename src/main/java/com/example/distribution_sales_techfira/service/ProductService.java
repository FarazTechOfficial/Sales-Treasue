package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.ProductReqDTO;
import com.example.distribution_sales_techfira.dto.ProductResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService extends BaseService<ProductReqDTO,ProductResDTO,Integer>{

    void importProductFromExcel(MultipartFile file) throws IOException;
    ProductResDTO getProductByCode(Integer code);
    CustomPageResponse<ProductResDTO> serachProduct(String keyword,int page, int size);
    CustomPageResponse<ProductResDTO> findAllPages(int page,int size,String sortField,String sortDir);
}
