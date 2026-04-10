package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.CreditDebitVendorReqDTO;
import com.example.distribution_sales_techfira.dto.CreditDebitVendorResDTO;
import com.example.distribution_sales_techfira.service.PdfService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfServiceImp implements PdfService {


    public ByteArrayOutputStream createPdfFromTransaction(CreditDebitVendorResDTO dto) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        doc.add(new Paragraph("Vendor: " + dto.getVendor().getVendorName()));
        doc.add(new Paragraph("Date: " + dto.getTransactionDate()));
        doc.add(new Paragraph("Amount: " + dto.getAmount()));
        doc.add(new Paragraph("Details: " + dto.getDescription()));
        doc.add(new Paragraph("Credit Note: " + dto.getCreditNote().getNoteName()));
        doc.add(new Paragraph("Debit Note: " + dto.getDebitNote().getNoteName()));

        doc.close();
        return out;
    }

}
