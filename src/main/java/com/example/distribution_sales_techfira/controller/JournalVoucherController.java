package com.example.distribution_sales_techfira.controller;
import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.JournalVoucherReqDTO;
import com.example.distribution_sales_techfira.dto.JournalVoucherResDTO;
import com.example.distribution_sales_techfira.dto.StatusDTO;
import com.example.distribution_sales_techfira.service.JournalVoucherService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/journal-voucher")
//@RequiredArgsConstructor
public class JournalVoucherController extends BaseController<JournalVoucherReqDTO,JournalVoucherResDTO, Integer> {

    private final JournalVoucherService journalVoucherService;

    public JournalVoucherController(JournalVoucherService journalVoucherService) {
        super(journalVoucherService);
        this.journalVoucherService = journalVoucherService;
    }
    @GetMapping("/without-pagination")
    public ResponseEntity<List<JournalVoucherResDTO>> getAll() {
        List<JournalVoucherResDTO> result = journalVoucherService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<CustomPageResponse<JournalVoucherResDTO>> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        int zeroBasedPage = Math.max(page -1,0);
        CustomPageResponse<JournalVoucherResDTO> result = journalVoucherService.findAllPaged(zeroBasedPage, size);
        return ResponseEntity.ok(result);
    }


    @Override
    protected String getEntityName() {

        return "journal-voucher";
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        journalVoucherService.uploadJournalVouchersFromExcel(file);
        return ResponseEntity.ok("Excel uploaded  successfully.");
    }

    @GetMapping("/sample-download")
    public ResponseEntity<Resource> downloadSampleFile() throws IOException {
        ByteArrayInputStream in = journalVoucherService.generateSampleExcel();
        InputStreamResource file = new InputStreamResource(in);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=journal_voucher_sample.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }

    @GetMapping("/download/file")
    public ResponseEntity<byte[]> downloadAllJournalVouchers() throws IOException {
        ByteArrayInputStream in = journalVoucherService.downloadAllJournalVouchersExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=journal_vouchers.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }

}
