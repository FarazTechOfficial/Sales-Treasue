package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.JournalVoucherReqDTO;
import com.example.distribution_sales_techfira.dto.JournalVoucherResDTO;
import com.example.distribution_sales_techfira.dto.VendorResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface JournalVoucherService extends BaseService<JournalVoucherReqDTO,JournalVoucherResDTO,Integer>{

    void uploadJournalVouchersFromExcel(MultipartFile file);

    ByteArrayInputStream generateSampleExcel() throws IOException;

    ByteArrayInputStream downloadAllJournalVouchersExcel() throws IOException;
}

