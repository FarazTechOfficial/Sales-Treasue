package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.NoteResDTO;
import com.example.distribution_sales_techfira.dto.VendorReqDTO;
import com.example.distribution_sales_techfira.dto.VendorResDTO;
import com.example.distribution_sales_techfira.entity.Vendor;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface VendorService extends BaseService<VendorReqDTO,VendorResDTO,Integer>{
    String deleteVendor(Integer id);
    void importVendorsFromExcel(MultipartFile file) throws IOException;
    List<VendorResDTO> search(String text);
    List<VendorResDTO> findAllWithoutPagination();


}
