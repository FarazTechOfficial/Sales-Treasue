package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.CompanyReqDTO;
import com.example.distribution_sales_techfira.dto.CompanyResDTO;
import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.entity.Branch;
import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.entity.License;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.CompanyMapper;
import com.example.distribution_sales_techfira.repository.CompanyRepository;
import com.example.distribution_sales_techfira.service.CompanyService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImp implements CompanyService {


    private final CompanyRepository companyRepository;
    private final CompanyMapper mapper;
    private final AuditUtil auditUtil;
    public CompanyServiceImp(CompanyRepository companyRepository, CompanyMapper mapper, AuditUtil auditUtil) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
        this.auditUtil = auditUtil;
    }



    @Override
    public CompanyResDTO save(CompanyReqDTO companyReqDTO) {

        if (companyReqDTO.getStatus() == null){
             companyReqDTO.setStatus(2);
        }
        auditUtil.createAudit(companyReqDTO);
        Company entity = mapper.toEntity(companyReqDTO);
        entity.setStatus(2);
        Company saved = companyRepository.save(entity);

        return mapper.toDTO(saved);
    }

    @Override
    public CompanyResDTO findByID(Integer id) {
        Company company = companyRepository.findById(id).orElseThrow (
                () -> new CustomException("No such ID present"));

        return mapper.toDTO(company);
    }
    @Override
    public List<CompanyResDTO> findAll() {
        List<Company> companies = companyRepository.findByStatusNot(3);
        return mapper.toDTOList(companies);
    }


    @Override
    public CustomPageResponse<CompanyResDTO> findAllPaged(int page, int size) {
        // Creating pageable object
        Pageable pageable = PageRequest.of(page, size);

        // Get paged result from the repository
        Page<Company> pagedResult = companyRepository.findByStatusNot(3,pageable);

        // Convert the content of the page to DTOs
        List<CompanyResDTO> content = mapper.toDTOList(pagedResult.getContent());

        // Return CustomPageResponse with the correct arguments
        return new CustomPageResponse<CompanyResDTO>(
                content, // List of DTOs
                pagedResult.getSort().isUnsorted(), // Unsorted flag
                pagedResult.getSort().isSorted(),   // Sorted flag
                pagedResult.getTotalElements(),     // Total number of elements
                size,                               // Page size
                page + 1                           // Page number (1-based)
        );
    }



    @Override
    public void softDeleteById(Integer id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CustomException("Company Id does not exist!"));
        company.setStatus(3);
        System.out.println(company.getId());
        System.out.println(company.getStatus());
        List<Branch> branches = company.getBranches();
        List<Branch> collect = branches.stream().peek(branch -> branch.setStatus(3)).collect(Collectors.toList());
        License licence = company.getLicence();
        if(company.getLicence() !=null){
            licence.setStatus(3);
        }
        company.setBranches(branches);
        company.setLicence(licence);
        companyRepository.save(company);
    }

    @Override
    public CompanyResDTO update(Integer id,CompanyReqDTO companyReqDTO) {
        System.out.println("update call");
        Company company = companyRepository.findById(id).orElseThrow (
                () -> new CustomException("Company ID does not exists"));
        auditUtil.updateAudit(companyReqDTO);
        if (companyReqDTO.getName() != null){
            company.setName(companyReqDTO.getName());
        }
        if (companyReqDTO.getEmail() != null){
            company.setEmail(companyReqDTO.getEmail());
        }
        if (companyReqDTO.getPhone() != null){
            company.setPhone(companyReqDTO.getPhone());
        }
        if (companyReqDTO.getAddress() != null){
            company.setAddress(companyReqDTO.getAddress());
        }
        if (companyReqDTO.getUpdatedBy() != null) {
            company.setUpdatedBy(companyReqDTO.getUpdatedBy());
        }
        company.setStatus(2);
        Company saved = companyRepository.save(company);
        return mapper.toDTO(saved);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CustomException("Company Id does not exists"));
        company.setStatus(status);
        companyRepository.save(company);

    }

    @Override
    public List<CompanyResDTO> findAllBanksCreatedBy(Integer userId) {
        List<Company> companies = companyRepository.findByCreatedBy(userId);
        return mapper.toDTOList(companies);
    }

    @Override
    public List<CompanyResDTO> findAllBanksUpdatedBy(Integer userId) {
        List<Company> companies = companyRepository.findByUpdatedBy(userId);
        return mapper.toDTOList(companies);
    }


}