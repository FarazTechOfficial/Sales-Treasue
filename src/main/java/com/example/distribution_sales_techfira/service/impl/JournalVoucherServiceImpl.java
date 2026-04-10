package com.example.distribution_sales_techfira.service.impl;
import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.entity.*;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.CompanyMapper;
import com.example.distribution_sales_techfira.mapper.JournalVoucherMapper;
import com.example.distribution_sales_techfira.repository.*;
import com.example.distribution_sales_techfira.service.JournalVoucherService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class JournalVoucherServiceImpl implements JournalVoucherService {


    private final JournalVoucherRepository journalVoucherRepository;
    private final JournalVoucherNoteRepository journalVoucherNoteRepository;
    private final CompanyRepository companyRepository;
    private final NotesRepository notesRepository;
    private final JournalVoucherMapper mapper;
    private final CompanyMapper companyMapper;
    private final AuditUtil auditUtil;


    //Create JournalVoucher
    @Override
    public JournalVoucherResDTO save(JournalVoucherReqDTO dto) {
        Company company = companyRepository.findById(dto.getCompany().getId())
                .orElseThrow(() -> new CustomException("Company not found"));
        auditUtil.createAudit(dto);
        JournalVouchers voucher = mapper.toEntity(dto);
        voucher.setCompany(company);
        if (dto.getStatus() == null) {
            voucher.setStatus(2); // default to active
        } else {
            voucher.setStatus(dto.getStatus());
        }

        voucher = journalVoucherRepository.save(voucher);
        JournalVoucherNote creditNote = mapper.toNoteEntity(dto.getCreditNote(), voucher, getNote(dto.getCreditNote().getNoteId()), "credit");
        JournalVoucherNote debitNote = mapper.toNoteEntity(dto.getDebitNote(), voucher, getNote(dto.getDebitNote().getNoteId()), "debit");
        journalVoucherNoteRepository.saveAll(List.of(creditNote, debitNote));
        return mapper.toDetailedDTO(voucher, creditNote, debitNote);
    }
    private Note getNote(Integer id) {
        return notesRepository.findById(id).orElseThrow(() -> new CustomException("Note not found"));
    }
    //Get All
    @Override
    public List<JournalVoucherResDTO> findAll() {
        return journalVoucherRepository.findByStatusNot(3)
                .stream()
                .map(this::buildResWithNotes)
                .toList();
    }

    //Get By id
    @Override
    public JournalVoucherResDTO findByID(Integer id) {
        JournalVouchers voucher = journalVoucherRepository.findByIdAndStatusNot(id, 3)
                .orElseThrow(() -> new CustomException("Journal Voucher not found or deleted"));
        return buildResWithNotes(voucher);
    }

    // Builds response DTO by fetching credit and debit notes from the JournalVoucherNote table for the given voucher
    private JournalVoucherResDTO buildResWithNotes(JournalVouchers voucher) {
        List<JournalVoucherNote> notes = journalVoucherNoteRepository.findByJournalVoucherId(voucher.getId());
        JournalVoucherNote credit = notes.stream().filter(n -> "credit".equals(n.getNoteType())).findFirst().orElse(null);
        JournalVoucherNote debit = notes.stream().filter(n -> "debit".equals(n.getNoteType())).findFirst().orElse(null);
        return mapper.toDetailedDTO(voucher, credit, debit);
    }
    //Update Voucher
    @Override
    @Transactional
    public JournalVoucherResDTO update(Integer id, JournalVoucherReqDTO dto) {
        JournalVouchers voucher = journalVoucherRepository.findById(id)
                .orElseThrow(() -> new CustomException("Voucher not found"));
        Company company = companyRepository.findById(dto.getCompany().getId())
                .orElseThrow(() -> new CustomException("Company not found"));
        auditUtil.updateAudit(dto);
        // Update voucher fields
        voucher.setTransactionDate(dto.getTransactionDate());
        voucher.setDescription(dto.getDescription());
        voucher.setCompany(company);
        if (dto.getStatus() != null) {
            voucher.setStatus(dto.getStatus());
        }
        if (dto.getUpdatedBy() != null) {
            voucher.setUpdatedBy(dto.getUpdatedBy());
        }
        journalVoucherRepository.save(voucher);
        // Delete old notes
        journalVoucherNoteRepository.deleteByJournalVoucherId(id);
        // Create new notes
        JournalVoucherNote creditNote = mapper.toNoteEntity(
                dto.getCreditNote(), voucher, getNote(dto.getCreditNote().getNoteId()), "credit");
        JournalVoucherNote debitNote = mapper.toNoteEntity(
                dto.getDebitNote(), voucher, getNote(dto.getDebitNote().getNoteId()), "debit");
        journalVoucherNoteRepository.saveAll(List.of(creditNote, debitNote));
        // Return updated response
        return mapper.toDetailedDTO(voucher, creditNote, debitNote);
    }
    //Delete
    @Override
    @Transactional
    public void softDeleteById(Integer id) {
        JournalVouchers voucher = journalVoucherRepository.findById(id)
                .orElseThrow(() -> new CustomException("Voucher not found"));
        voucher.setStatus(3); // mark as deleted
        journalVoucherRepository.save(voucher);
    }

    //Pagination
    @Override
    public CustomPageResponse<JournalVoucherResDTO> findAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JournalVouchers> pagedResult = journalVoucherRepository.findByStatusNot(3, pageable);
        List<JournalVoucherResDTO> content = pagedResult.getContent()
                .stream()
                .map(this::buildResWithNotes)
                .toList();
        return new CustomPageResponse<>(
                content,
                pagedResult.getSort().isUnsorted(),
                pagedResult.getSort().isSorted(),
                pagedResult.getTotalElements(),
                size,
                page + 1
        );
    }
    //Status
    @Override
    @Transactional
    public void updateStatus(Integer id, Integer status) {
        if (status < 0 || status > 3) {
            throw new CustomException("Invalid status. Must be between 0 and 3.");
        }
        JournalVouchers voucher = journalVoucherRepository.findById(id)
                .orElseThrow(() -> new CustomException("Voucher not found"));
        voucher.setStatus(status);
        journalVoucherRepository.save(voucher);
    }

    @Override
    public List<JournalVoucherResDTO> findAllBanksCreatedBy(Integer userId) {
        List<JournalVouchers> journalVouchers = journalVoucherRepository.findByCreatedBy(userId);
        return mapper.toDTOList(journalVouchers);

    }

    @Override
    public List<JournalVoucherResDTO> findAllBanksUpdatedBy(Integer userId) {

        List<JournalVouchers> journalVouchers = journalVoucherRepository.findByUpdatedBy(userId);
        return mapper.toDTOList(journalVouchers);
    }


    @Override
    @Transactional
    public void uploadJournalVouchersFromExcel(MultipartFile file) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                JournalVoucherReqDTO dto = new JournalVoucherReqDTO();
                Cell cell = row.getCell(0);
                if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    dto.setTransactionDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                } else {
                    String dateStr = getCellValueAsString(cell);
                    try {
                        dto.setTransactionDate(LocalDate.parse(dateStr));
                    } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Invalid date format at row " + (i + 1));
                    }

                }
                Cell descCell = row.getCell(1);
                if (descCell != null) {
                    dto.setDescription(getCellValueAsString(descCell));
                }
                Cell companyCell = row.getCell(2);
                if (companyCell != null) {
                    String companyName = getCellValueAsString(companyCell);
                    Company company = companyRepository.findByNameIgnoreCase(companyName)

                            .orElseThrow(() -> new CustomException("Company not found: " + companyName));
                    dto.setCompany(new CompanyReqDTO(
                            company.getId(),
                            company.getName(),
                            company.getEmail(),
                            company.getPhone(),
                            company.getStatus(),
                            company.getAddress()
                    ));
                }

                Cell creditNoteCell = row.getCell(3);
                String creditNoteName = getCellValueAsString(creditNoteCell);
                Cell debitNoteCell = row.getCell(4);
                String debitNoteName = getCellValueAsString(debitNoteCell);
                Cell creditAmountCell = row.getCell(5);
                Double creditAmount = getCellValueAsDouble(creditAmountCell);
                Cell debitAmountCell = row.getCell(6);
                Double debitAmount = getCellValueAsDouble(debitAmountCell);
                Note creditNote = notesRepository.findAllCreditNotes(creditNoteName);
                Note debitNote = notesRepository.findAllDebitNotes(debitNoteName);

                dto.setCreditNote(new NoteAmountDTO(creditNote.getId(), creditNote.getNoteName(), creditAmount));
                dto.setDebitNote(new NoteAmountDTO(debitNote.getId(), debitNote.getNoteName(), debitAmount));
                Cell statusCell = row.getCell(7);
                if (statusCell != null) {
                    try {
                        String statusValue = getCellValueAsString(statusCell).toLowerCase().trim();
                        if (statusValue.equals("active") || statusValue.equals("2")) {
                            dto.setStatus(2);
                        } else if (statusValue.equals("inactive") || statusValue.equals("1")) {
                            dto.setStatus(1);
                        } else {
                            throw new CustomException("Invalid status value '" + statusValue + "' at row " + (i + 1) + ". Use 'active', 'inactive', '1', or '2'");
                        }
                    } catch (Exception e) {
                        throw new CustomException("Invalid status at row " + (i + 1) + ": " + e.getMessage());
                    }
                }
                save(dto);
            }
        } catch (Exception e) {
            throw new CustomException("Failed to process Excel: " + e.getMessage());
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private Double getCellValueAsDouble(Cell cell) {
        if (cell == null) return 0.0;

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    String stringValue = cell.getStringCellValue().trim();
                    if (stringValue.isEmpty()) return 0.0;
                    return Double.parseDouble(stringValue);
                } catch (NumberFormatException e) {
                    throw new CustomException("Invalid numeric value: " + cell.getStringCellValue());
                }
            case FORMULA:
                try {
                    return cell.getNumericCellValue();
                } catch (Exception e) {
                    throw new CustomException("Cannot evaluate formula cell as numeric value");
                }
            default:
                return 0.0;
        }
    }
    //Download Sample
    @Override
    public ByteArrayInputStream generateSampleExcel() throws IOException {
        String[] columns = {"Transaction Date", "Description", "Company Name", "Credit Note", "Debit Note", "Credit Amount", "Debit Amount","status"
        };
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Sample");
            // Create Header Row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }
            // Optionally add sample data
            Row sampleRow = sheet.createRow(1);
            sampleRow.createCell(0).setCellValue("2025-05-30");sampleRow.createCell(1).setCellValue("Sample Description");
            sampleRow.createCell(2).setCellValue("Team Techfira");sampleRow.createCell(3).setCellValue("Credit Note A");sampleRow.createCell(4).setCellValue("Debit Note B");
            sampleRow.createCell(5).setCellValue(1000.0);sampleRow.createCell(6).setCellValue(1000.0);
            sampleRow.createCell(7).setCellValue(2);
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
    //Download Availble Data Files
    @Override
    public ByteArrayInputStream downloadAllJournalVouchersExcel() throws IOException {
        String[] columns = {
                "Transaction Date", "Description", "Company Name",
                "Credit Note Name", "Debit Note Name",
                "Credit Amount", "Debit Amount", "Status"
        };

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Journal Vouchers");
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            List<JournalVouchers> vouchers = journalVoucherRepository.findByStatusNot(3);
            int rowIdx = 1;
            for (JournalVouchers voucher : vouchers) {
                List<JournalVoucherNote> notes = journalVoucherNoteRepository.findByJournalVoucherId(voucher.getId());
                JournalVoucherNote creditNote = notes.stream()
                        .filter(n -> "credit".equals(n.getNoteType())).findFirst().orElse(null);
                JournalVoucherNote debitNote = notes.stream()
                        .filter(n -> "debit".equals(n.getNoteType())).findFirst().orElse(null);

                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(voucher.getTransactionDate().toString());
                row.createCell(1).setCellValue(voucher.getDescription());
                row.createCell(2).setCellValue(voucher.getCompany().getName());
                row.createCell(3).setCellValue(creditNote != null ? creditNote.getNote().getNoteName() : "");
                row.createCell(4).setCellValue(debitNote != null ? debitNote.getNote().getNoteName() : "");
                row.createCell(5).setCellValue(creditNote != null ? creditNote.getAmount() : 0);
                row.createCell(6).setCellValue(debitNote != null ? debitNote.getAmount() : 0);
                row.createCell(7).setCellValue(voucher.getStatus());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }


    public CompanyMapper getCompanyMapper() {
        return companyMapper;
    }



}



