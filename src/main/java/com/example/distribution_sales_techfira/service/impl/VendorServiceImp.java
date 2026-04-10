package com.example.distribution_sales_techfira.service.impl;


import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.entity.Bank;
import com.example.distribution_sales_techfira.entity.Note;
import com.example.distribution_sales_techfira.entity.Vendor;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.VendorMapper;
import com.example.distribution_sales_techfira.repository.BankRepository;
import com.example.distribution_sales_techfira.repository.VendorRepository;
import com.example.distribution_sales_techfira.service.VendorService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorServiceImp implements VendorService {


    private final VendorRepository vendorRepository;
    private final BankRepository bankRepository;
    private final VendorMapper mapper;
    private final AuditUtil auditUtil;
    public VendorServiceImp(VendorRepository vendorRepository, BankRepository bankRepository, VendorMapper mapper, AuditUtil auditUtil) {
        this.vendorRepository = vendorRepository;
        this.bankRepository = bankRepository;
        this.mapper = mapper;
        this.auditUtil = auditUtil;
    }
    @Override
    public VendorResDTO save(VendorReqDTO dto) {
        Optional<Bank> byId = bankRepository.findById(dto.getBank().getId());
        Bank bank = bankRepository.findById(dto.getBank().getId()).orElseThrow(() -> new CustomException("Bank id does not exist"));
        dto.setBank(bank);
        if (dto.getStatus() == null){
            dto.setStatus(2);
        }
       auditUtil.createAudit(dto);
        Vendor vendor = mapper.toEntity(dto);
        vendor.setStatus(2);
        Vendor saved = vendorRepository.save(vendor);
        // Generate vendorCode after ID is generated
        saved.setVendorCode(String.format("VND%02d", saved.getId()));
        Vendor result = vendorRepository.save(saved);
        System.out.println(result.getVendorCode());
        return mapper.toDTO(result);
    }

    @Override
    public VendorResDTO findByID(Integer id) {
        Vendor result = vendorRepository.findById(id).orElseThrow(() -> new CustomException("Id does not exist"));
        return mapper.toDTO(result);
    }

    @Override
    public List<VendorResDTO> findAll() {
        List<Vendor> vendors = vendorRepository.findByStatusNot(3);
        return VendorMapper.toListDTO(vendors);
    }

    @Override
    public CustomPageResponse<VendorResDTO> findAllPaged(int page, int size) {

        Pageable pages = PageRequest.of(page, size);
        Page<Vendor> pagedResult = vendorRepository.findByStatusNot(3,pages);
        List<VendorResDTO> content = mapper.toListDTO(pagedResult.getContent());
        return new CustomPageResponse<VendorResDTO>(
                content, // List of DTOs
                pagedResult.getSort().isUnsorted(), // Unsorted flag
                pagedResult.getSort().isSorted(),   // Sorted flag
                pagedResult.getTotalElements(),     // Total number of elements
                size,                               // Page size
                page + 1                           // Page number (1-based)
        );
    }
    @Override
    public void importVendorsFromExcel(MultipartFile file) throws IOException {
        List<Vendor> vendors = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || row.getCell(0) == null) continue;
                Vendor vendor = new Vendor();
                vendor.setVendorName(getCellValue(row.getCell(0)));
                vendor.setContactNumber(getCellValue(row.getCell(1)));
                vendor.setAddress(getCellValue(row.getCell(2)));
                vendor.setEmailAddress(getCellValue(row.getCell(3)));
                vendor.setAccountNumber(getCellValue(row.getCell(4)));
                String statusStr = getCellValue(row.getCell(5));
                String bankStr = getCellValue(row.getCell(6));
                int finalI = i;
                Bank bank = bankRepository.findByBankNameIgnoreCase(bankStr)
                        .orElseThrow(() -> new CustomException("Bank does not exist on row :"+ finalI));
                vendor.setBank(bank);

                if (statusStr != null && !statusStr.isBlank()) {
                    switch (statusStr.trim().toLowerCase()) {
                        case "active":
                            vendor.setStatus(2);
                            break;
                        case "inactive":
                            vendor.setStatus(1);
                            break;
//                        case "delete":
//                            vendor.setStatus(3);
//                            break;
                        default:
                            throw new IllegalArgumentException("Invalid status at row " + (i + 1) + ". Must be 'active', 'inactive', or 'delete'.");
                    }
                } else {
                    vendor.setStatus(2); // default to 'inactive'
                }

                vendors.add(vendor);
            }
        }
        List<Vendor> savedVendors = vendorRepository.saveAll(vendors);
        List<Vendor> result = savedVendors.stream()
                .peek(vendor -> vendor.setVendorCode(String.format("VND%02d", vendor.getId())))
                .collect(Collectors.toList());
        vendorRepository.saveAll(result);
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
    @Override
    public VendorResDTO update(Integer id, VendorReqDTO dto) {
        Vendor newVendor = vendorRepository.findById(id).orElseThrow(() -> new CustomException("Id does not exist"));
        auditUtil.updateAudit(dto);
        if (dto.getVendorName() != null){
            newVendor.setVendorName(dto.getVendorName());
        }
        if (dto.getAddress() != null){
            newVendor.setAddress(dto.getAddress());
        }
        if (dto.getEmailAddress() != null){
            newVendor.setEmailAddress(dto.getEmailAddress());
        }
        if (dto.getAccountNumber() != null){
            newVendor.setAccountNumber(dto.getAccountNumber());
        }
        if (dto.getContactNumber() != null){
            newVendor.setContactNumber(dto.getContactNumber());
        }
        if (dto.getUpdatedBy() != null) {
            newVendor.setUpdatedBy(dto.getUpdatedBy());
        }
        Bank bank = bankRepository.findById(dto.getBank().getId()).orElseThrow(() -> new CustomException("Bank Id does not exists"));
        newVendor.setBank(bank);
        newVendor.setStatus(2);
        Vendor result = vendorRepository.save(newVendor);
        return mapper.toDTO(result);
    }
    @Override
    public String deleteVendor(Integer id) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow(() -> new CustomException("Sorry Id does not exists"));
        vendor.setStatus(3);
        vendorRepository.save(vendor);
        return new String("Deleted");
    }
    @Override
    public void updateStatus(Integer id, Integer status) {
        Vendor vendorIdDoesNotExists = vendorRepository.findById(id).orElseThrow(() -> new CustomException("Vendor Id does not exists"));
        vendorIdDoesNotExists.setStatus(status);
        System.out.println(vendorIdDoesNotExists.getStatus());
        vendorRepository.save(vendorIdDoesNotExists);
    }
    @Override
    public List<VendorResDTO> search(String text) {
//        List<Vendor> vendors = vendorRepository.searchAcrossFields(text);
        return new ArrayList<>();
    }

    @Override
    public List<VendorResDTO> findAllWithoutPagination() {
        return mapper.toListDTO(vendorRepository.findByStatus(2));
    }

    @Override
    public List<VendorResDTO> findAllBanksCreatedBy(Integer userId) {
        List<Vendor> vendors = vendorRepository.findByCreatedBy(userId);
        return mapper.toListDTO(vendors);
    }

    @Override
    public List<VendorResDTO> findAllBanksUpdatedBy(Integer userId) {
        List<Vendor> vendors = vendorRepository.findByUpdatedBy(userId);
        return mapper.toListDTO(vendors);
    }
}
