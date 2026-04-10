package com.example.distribution_sales_techfira.controller;

import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.entity.BaseEntity;
import com.example.distribution_sales_techfira.entity.CreditDebitVendor;
import com.example.distribution_sales_techfira.service.CreditDebitVendorService;
import com.example.distribution_sales_techfira.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/credit-debit-vendor")
public class CreditDebitVendorController extends BaseController<CreditDebitVendorReqDTO,CreditDebitVendorResDTO,Integer>{
    private final CreditDebitVendorService creditDebitVendorService;
    private final PdfService pdfService;
    public CreditDebitVendorController(CreditDebitVendorService creditDebitVendorService, PdfService pdfService) {
        super(creditDebitVendorService);
        this.creditDebitVendorService = creditDebitVendorService;
        this.pdfService = pdfService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<CustomPageResponse<CreditDebitVendorResDTO>>>
    getAll(@RequestParam(defaultValue = "1") int page,
           @RequestParam(defaultValue = "10") int size) {
        int zeroBasedPage = Math.max(page - 1, 0);
        CustomPageResponse<CreditDebitVendorResDTO> result =
                creditDebitVendorService.getAllCreditDebitVendors(zeroBasedPage,size);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }
    @GetMapping("/debit")
    public ResponseEntity<BaseResponse<CustomPageResponse<CreditDebitVendorResDTO>>>
    getAllDebit(@RequestParam(defaultValue = "1") int page,
                @RequestParam(defaultValue = "10") int size) {
        int zeroBasedPage = Math.max(page - 1, 0);
        CustomPageResponse<CreditDebitVendorResDTO> result =
                creditDebitVendorService.getAllDebitVendors(zeroBasedPage,size);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }

    @GetMapping("/credit")
    public ResponseEntity<BaseResponse<CustomPageResponse<CreditDebitVendorResDTO>>>
    getAllCredit(@RequestParam(defaultValue = "1") int page,
                 @RequestParam(defaultValue = "10") int size) {
        int zeroBasedPage = Math.max(page - 1, 0);
        CustomPageResponse<CreditDebitVendorResDTO> result =
                creditDebitVendorService.findAllPaged(zeroBasedPage,size);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable Integer id) {
        String result = creditDebitVendorService.delete(id);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }

    @Override
    protected String getEntityName() {
        return "CreditDebitVendor";
    }


    @GetMapping("/pdf/{id}")
    public void generatePdf(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        CreditDebitVendorResDTO creditDebitVendorResDTO = creditDebitVendorService.findByID(id);
        // Use iText or similar library to create PDF
        ByteArrayOutputStream baos = pdfService.createPdfFromTransaction(creditDebitVendorResDTO);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=transaction_" + id + ".pdf");
        response.getOutputStream().write(baos.toByteArray());
    }


    @PostMapping("/upload-excel")
    public ResponseEntity<Map<String, String>> uploadVendorExcel(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("message", "Please upload a valid Excel file.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            creditDebitVendorService.importVendorsFromExcel(file);
            response.put("message", "Excel uploaded successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error while uploading file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
