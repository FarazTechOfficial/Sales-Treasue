package com.example.distribution_sales_techfira.service.impl;



import com.example.distribution_sales_techfira.dto.CreditDebitVendorReqDTO;
import com.example.distribution_sales_techfira.dto.CreditDebitVendorResDTO;
import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.entity.CreditDebitVendor;
import com.example.distribution_sales_techfira.entity.Note;
import com.example.distribution_sales_techfira.entity.Vendor;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.CreditDebitVendorMapper;
import com.example.distribution_sales_techfira.repository.CreditDebitVendorRepository;
import com.example.distribution_sales_techfira.repository.NotesRepository;
import com.example.distribution_sales_techfira.repository.VendorRepository;
import com.example.distribution_sales_techfira.service.CreditDebitVendorService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class CreditDebitVendorServiceImp implements CreditDebitVendorService {

    private final CreditDebitVendorRepository creditVendorRepository;

    private final NotesRepository  notesRepository;
    private final VendorRepository vendorRepository;
    private final CreditDebitVendorMapper mapper;
    private final AuditUtil auditUtil;


    public CreditDebitVendorServiceImp(CreditDebitVendorRepository creditVendorRepository, NotesRepository notesRepository, VendorRepository vendorRepository, CreditDebitVendorMapper mapper, AuditUtil auditUtil) {
        this.creditVendorRepository = creditVendorRepository;
        this.notesRepository = notesRepository;
        this.vendorRepository = vendorRepository;
        this.mapper = mapper;
        this.auditUtil = auditUtil;
    }


    @Override
    public CreditDebitVendorResDTO save(CreditDebitVendorReqDTO creditDebitVendorReqDTO) {

        auditUtil.createAudit(creditDebitVendorReqDTO);
        // Validate Credit Note
        Note creditNote = null;
        if (creditDebitVendorReqDTO.getCreditNote() != null) {
            creditNote = notesRepository.findByIdAndNoteType(creditDebitVendorReqDTO.getCreditNote().getId(),"Credit")
                    .orElseThrow(() -> new RuntimeException("Invalid credit note ID"));
        }

        // Validate Debit Note
        Note debitNote = null;
        if (creditDebitVendorReqDTO.getDebitNote() != null) {
            debitNote = notesRepository.findByIdAndNoteType(creditDebitVendorReqDTO.getDebitNote().getId(),"Debit")
                    .orElseThrow(() -> new RuntimeException("Invalid debit note ID"));
        }

        // Validate Vendor
        Vendor vendor = vendorRepository.findById(creditDebitVendorReqDTO.getVendor().getId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        // Create transaction
        CreditDebitVendor transaction = new CreditDebitVendor();
        transaction.setStatus(2);
        transaction.setVendor(vendor);
        transaction.setTransactionDate(creditDebitVendorReqDTO.getTransactionDate());
        transaction.setAmount(creditDebitVendorReqDTO.getAmount());
        transaction.setDescription(creditDebitVendorReqDTO.getDescription());
        transaction.setTransactionType(creditDebitVendorReqDTO.getTransactionType());
        transaction.setCreditNote(creditNote);
        transaction.setDebitNote(debitNote);
        transaction.setCreatedBy(creditDebitVendorReqDTO.getCreatedBy());

        CreditDebitVendor saved = creditVendorRepository.save(transaction);
        System.out.println(saved.getAmount());
        return mapper.toDTO(saved);
    }

    @Override
    public String delete(Integer id) {
        CreditDebitVendor creditDebitVendor = creditVendorRepository.findById(id).orElseThrow(() -> new CustomException("No such Id exists"));
        creditDebitVendor.setStatus(3);
        creditDebitVendor.setDeletedAt(LocalDate.now());
        creditVendorRepository.save(creditDebitVendor);
        return "Deleted!";
    }

    @Override
    public CustomPageResponse<CreditDebitVendorResDTO> getAllCreditDebitVendors(int page, int size) {
        // Creating pageable object
        Pageable pageable = PageRequest.of(page, size);

        // Get paged result from the repository
        Page<CreditDebitVendor> pagedResult = creditVendorRepository.findByStatusNot(3,pageable);

        // Convert the content of the page to DTOs
        List<CreditDebitVendorResDTO> content = mapper.toListDTO(pagedResult.getContent());

        // Return CustomPageResponse with the correct arguments
        return new CustomPageResponse<CreditDebitVendorResDTO>(
                content, // List of DTOs
                pagedResult.getSort().isUnsorted(), // Unsorted flag
                pagedResult.getSort().isSorted(),   // Sorted flag
                pagedResult.getTotalElements(),     // Total number of elements
                size,                               // Page size
                page + 1                           // Page number (1-based)
        );
    }

    @Override
    public CustomPageResponse<CreditDebitVendorResDTO> getAllDebitVendors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<CreditDebitVendor> allActiveDebitTransactions = creditVendorRepository.findAllActiveDebitTransactions(pageable);

        List<CreditDebitVendorResDTO> content = mapper.toListDTO(allActiveDebitTransactions.getContent());

        return new CustomPageResponse<CreditDebitVendorResDTO>(
                content,
                allActiveDebitTransactions.getSort().isUnsorted(),
                allActiveDebitTransactions.getSort().isSorted(),
                allActiveDebitTransactions.getTotalElements(),
                size,
                page +1
        );
    }

    @Override
    public CustomPageResponse<CreditDebitVendorResDTO> findAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<CreditDebitVendor> allActiveDebitTransactions = creditVendorRepository.findAllActiveCreditTransactions(pageable);

        List<CreditDebitVendorResDTO> content = mapper.toListDTO(allActiveDebitTransactions.getContent());

        return new CustomPageResponse<CreditDebitVendorResDTO>(
                content,
                allActiveDebitTransactions.getSort().isUnsorted(),
                allActiveDebitTransactions.getSort().isSorted(),
                allActiveDebitTransactions.getTotalElements(),
                size,
                page +1
        );
    }

    @Override
    public CreditDebitVendorResDTO update(Integer id, CreditDebitVendorReqDTO creditDebitVendorReqDTO) {
        CreditDebitVendor creditDebitVendor = creditVendorRepository.findById(id).orElseThrow(() -> new CustomException("Credit-Debit-Vendor ID does not exists"));
        auditUtil.updateAudit(creditDebitVendorReqDTO);
        if (creditDebitVendorReqDTO.getVendor() !=null){
            Vendor vendor = vendorRepository.findById(creditDebitVendorReqDTO.getVendor().getId()).orElseThrow(()-> new CustomException("Vendor ID does not exists"));
            creditDebitVendor.setVendor(vendor);

        }
        if (creditDebitVendorReqDTO.getAmount() !=null){
            creditDebitVendor.setAmount(creditDebitVendorReqDTO.getAmount());
        }
        if (creditDebitVendorReqDTO.getDescription() !=null){
            creditDebitVendor.setDescription(creditDebitVendorReqDTO.getDescription());
        }
        if (creditDebitVendorReqDTO.getTransactionDate() !=null){
            creditDebitVendor.setTransactionDate(creditDebitVendorReqDTO.getTransactionDate());
        }
        if (creditDebitVendorReqDTO.getCreditNote() !=null){
            Note credit = notesRepository.findByIdAndNoteType(creditDebitVendorReqDTO.getCreditNote().getId(), "Credit")
                    .orElseThrow(()-> new CustomException("Credit-Note by ID does not exists"));
            creditDebitVendor.setCreditNote(credit);
        }
        if (creditDebitVendorReqDTO.getDebitNote() !=null){
            Note debit = notesRepository.findByIdAndNoteType(creditDebitVendorReqDTO.getDebitNote().getId(), "Debit")
                    .orElseThrow(() -> new CustomException("Debit-Note by ID does not exists"));
            creditDebitVendor.setDebitNote(debit);
        }
        if (creditDebitVendorReqDTO.getUpdatedBy() != null) {
            creditDebitVendor.setUpdatedBy(creditDebitVendorReqDTO.getUpdatedBy());
        }


        creditDebitVendor.setStatus(2);
        CreditDebitVendor saved = creditVendorRepository.save(creditDebitVendor);
        return mapper.toDTO(saved);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        CreditDebitVendor creditDebitVendor = creditVendorRepository.findById(id).orElseThrow(() -> new CustomException("Transaction Id does not exists"));
        creditDebitVendor.setStatus(status);
        creditVendorRepository.save(creditDebitVendor);
    }

    @Override
    public CreditDebitVendorResDTO findByID(Integer id) {
        CreditDebitVendor creditDebitVendor = creditVendorRepository.findById(id).orElseThrow(() -> new CustomException("Credit-Debit-VendorId does not exists!"));
        return mapper.toDTO(creditDebitVendor);
    }

    @Override
    public void importVendorsFromExcel(MultipartFile file) throws IOException {
        List<CreditDebitVendor> creditVendors = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || row.getCell(0) == null) continue;

                CreditDebitVendor creditVendor = new CreditDebitVendor();

                // Vendor
                String vendorName = getCellValue(row.getCell(0));
                Vendor vendor = vendorRepository.findByVendorNameIgnoreCaseAndStatusNot(vendorName,3)
                        .orElseThrow(() -> new CustomException("Vendor not found at row " + ( 1)));
                creditVendor.setVendor(vendor);

                // Date
                Cell cell = row.getCell(1);
                if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    creditVendor.setTransactionDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                } else {
                    String dateStr = getCellValue(cell);
                    try {
                        creditVendor.setTransactionDate(LocalDate.parse(dateStr));
                    } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Invalid date format at row " + (i + 1));
                    }
                }

                // Amount
                Cell amountCell = row.getCell(2);
                if (amountCell != null && amountCell.getCellType() == CellType.NUMERIC) {
                    creditVendor.setAmount((float) amountCell.getNumericCellValue());
                } else {
                    creditVendor.setAmount(Float.parseFloat(getCellValue(amountCell)));
                }

                // Description
                creditVendor.setDescription(getCellValue(row.getCell(3)));

                // Status
                String statusStr = getCellValue(row.getCell(4)).trim().toLowerCase();
                switch (statusStr) {
                    case "active" -> creditVendor.setStatus(2);
                    case "inactive" -> creditVendor.setStatus(1);
                    default -> throw new IllegalArgumentException("Invalid status at row " + (i + 1) + ". Must be 'active' or 'inactive'.");
                }

                // Credit Note
                String creditNoteStr = getCellValue(row.getCell(5));
                Note creditNote = notesRepository.findAllCreditNotes(creditNoteStr);
                creditVendor.setCreditNote(creditNote);

                // Debit Note
                String debitNoteStr = getCellValue(row.getCell(6));
                Note debitNote = notesRepository.findAllDebitNotes(debitNoteStr);
                creditVendor.setDebitNote(debitNote);

                // Transaction Type
                creditVendor.setTransactionType(getCellValue(row.getCell(7)));

                creditVendors.add(creditVendor);
            }
        }

        creditVendorRepository.saveAll(creditVendors);
    }


    @Override
    public List<CreditDebitVendorResDTO> findAllBanksCreatedBy(Integer userId) {
        List<CreditDebitVendor> creditDebitVendors = creditVendorRepository.findByCreatedBy(userId);
        return mapper.toListDTO(creditDebitVendors);
    }

    @Override
    public List<CreditDebitVendorResDTO> findAllBanksUpdatedBy(Integer userId) {
        List<CreditDebitVendor> creditDebitVendors = creditVendorRepository.findByUpdatedBy(userId);
        return mapper.toListDTO(creditDebitVendors);
    }


    private String getCellValue(Cell cell){
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

}
