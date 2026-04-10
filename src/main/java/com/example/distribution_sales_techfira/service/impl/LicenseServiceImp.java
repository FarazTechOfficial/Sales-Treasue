package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.CompanyResDTO;
import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.LicenseReqDTO;
import com.example.distribution_sales_techfira.dto.LicenseResDTO;
import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.entity.License;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.LicenseMapper;
import com.example.distribution_sales_techfira.repository.CompanyRepository;
import com.example.distribution_sales_techfira.repository.LicenceRepository;
import com.example.distribution_sales_techfira.service.LicenseService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class LicenseServiceImp implements LicenseService {

    private final LicenceRepository licenceRepository;
    private final CompanyRepository companyRepository;
    private final LicenseMapper mapper;

    private final AuditUtil auditUtil;
    public LicenseServiceImp(LicenceRepository licenceRepository, CompanyRepository companyRepository, LicenseMapper mapper, AuditUtil auditUtil) {
        this.licenceRepository = licenceRepository;
        this.companyRepository = companyRepository;
        this.mapper = mapper;
        this.auditUtil = auditUtil;
    }

    @Override
    public LicenseResDTO save(LicenseReqDTO licenseReqDTO) {
        Company company = companyRepository.findById(licenseReqDTO.getCompany().getId())
                .orElseThrow(() -> new CustomException("Company Id does not exist"));

        // Check if license already exists for this company
        Optional<License> existingLicenseOpt = licenceRepository.findByCompanyId(company.getId());

        License licenseEntity;
        if (existingLicenseOpt.isPresent()) {
            // Update existing license
            License existingLicense = existingLicenseOpt.get();
            existingLicense.setStatus(2);
            existingLicense.setCompany(company);
            existingLicense.setValidFrom(licenseReqDTO.getValidFrom());
            existingLicense.setValidTo(licenseReqDTO.getValidTo());
            existingLicense.setNumUsers(licenseReqDTO.getNumUsers());
            // ... set other fields you want to update
            licenseEntity = existingLicense;
        } else {

            // Create new license
            auditUtil.createAudit(licenseReqDTO);
            License newLicense = mapper.toEntity(licenseReqDTO);
            newLicense.setCompany(company);
            newLicense.setStatus(2);
            licenseEntity = newLicense;
        }

        License saved = licenceRepository.save(licenseEntity);
        return mapper.toDTO(saved);
    }

    @Override
    public LicenseResDTO findByID(Integer id) {
        License license = licenceRepository.findById(id).orElseThrow(() -> new CustomException("License Id does not exists"));
        return mapper.toDTO(license);
    }

    @Override
    public List<LicenseResDTO> findAll() {
        List<License> all = licenceRepository.findByStatusNot(3);
        return mapper.toDTOList(all);
    }

    @Override
    public CustomPageResponse<LicenseResDTO> findAllPaged(int page, int size) {
        // Create a Pageable object for pagination
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paged results from the repository
        Page<License> pagedResult = licenceRepository.findByStatusNot(3,pageable);

        // Convert the list of License entities to DTOs
        List<LicenseResDTO> content = mapper.toDTOList(pagedResult.getContent());

        // Return the paginated response wrapped in CustomPageResponse
        return new CustomPageResponse<LicenseResDTO>(
                content,  // List of LicenseResDTOs
                pagedResult.getSort().isUnsorted(),  // Unsorted flag
                pagedResult.getSort().isSorted(),    // Sorted flag
                pagedResult.getTotalElements(),      // Total number of elements
                size,                                 // Page size
                page + 1                              // Page number (1-based)
        );
    }


    @Override
    public void softDeleteById(Integer id) {
        License license = licenceRepository.findById(id).orElseThrow(() -> new CustomException("Company Id does not exists"));
        license.setStatus(3);
        licenceRepository.save(license);
    }

    @Override
    public LicenseResDTO update(Integer id, LicenseReqDTO licenseReqDTO) {
        License license = licenceRepository.findById(id).orElseThrow(() -> new CustomException("License ID does not exists!"));
        auditUtil.updateAudit(licenseReqDTO);
        if (licenseReqDTO.getValidFrom() != null){
            license.setValidFrom(licenseReqDTO.getValidFrom());
        }
        if (licenseReqDTO.getValidTo() != null){
            license.setValidTo(licenseReqDTO.getValidTo());
        }
        if (licenseReqDTO.getNumUsers() != null){
            license.setNumUsers(licenseReqDTO.getNumUsers());
        }
        if (licenseReqDTO.getUpdatedBy() != null) {
            license.setUpdatedBy(licenseReqDTO.getUpdatedBy());
        }

        license.setStatus(2);
        license.setCompany(license.getCompany());
        License saved = licenceRepository.save(license);
        return mapper.toDTO(saved);
    }


    @Override
    public void updateStatus(Integer id, Integer status) {
        License licenseIdDoesNotExists = licenceRepository.findById(id).orElseThrow(() -> new CustomException("License Id does not exists"));
        licenseIdDoesNotExists.setStatus(status);
        System.out.println(licenseIdDoesNotExists.getStatus());
        licenceRepository.save(licenseIdDoesNotExists);
    }

    @Override
    public List<LicenseResDTO> findAllBanksCreatedBy(Integer userId) {
        List<License> license = licenceRepository.findByCreatedBy(userId);
        return mapper.toDTOList(license);
    }

    @Override
    public List<LicenseResDTO> findAllBanksUpdatedBy(Integer userId) {
        List<License> license = licenceRepository.findByUpdatedBy(userId);
        return mapper.toDTOList(license);
    }


}
