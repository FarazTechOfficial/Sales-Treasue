package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.CreditDebitVendorReqDTO;
import com.example.distribution_sales_techfira.dto.CreditDebitVendorResDTO;

import java.io.ByteArrayOutputStream;

public interface PdfService {
    ByteArrayOutputStream createPdfFromTransaction(CreditDebitVendorResDTO dto) throws Exception;
}
