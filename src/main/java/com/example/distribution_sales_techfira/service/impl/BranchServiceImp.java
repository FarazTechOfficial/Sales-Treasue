package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.BankResDTO;
import com.example.distribution_sales_techfira.dto.BranchReqDTO;
import com.example.distribution_sales_techfira.dto.BranchResDTO;
import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.entity.Bank;
import com.example.distribution_sales_techfira.entity.Branch;
import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.BranchMapper;
import com.example.distribution_sales_techfira.repository.BranchRepository;
import com.example.distribution_sales_techfira.repository.CompanyRepository;
import com.example.distribution_sales_techfira.service.BranchService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImp implements BranchService {

    private final BranchRepository branchRepository;
    private final CompanyRepository companyRepository;
    private final BranchMapper mapper;
    private final AuditUtil auditUtil;

    public BranchServiceImp(BranchRepository branchRepository, CompanyRepository companyRepository, BranchMapper mapper, AuditUtil auditUtil) {
        this.branchRepository = branchRepository;
        this.companyRepository = companyRepository;
        this.mapper = mapper;
        this.auditUtil = auditUtil;
    }

    @Override
    public BranchResDTO save(BranchReqDTO branchReqDTO) {
        Company company = companyRepository.findById(branchReqDTO.getCompany().getId()).orElseThrow(
                () -> new CustomException("Company Id does not exists"));
        auditUtil.createAudit(branchReqDTO);
        branchReqDTO.setCompany(company);
        branchReqDTO.setStatus(2);
        Branch saved = branchRepository.save(mapper.toEntity(branchReqDTO));
        return mapper.toDTO(saved);
    }

    @Override
    public BranchResDTO findByID(Integer id) {
        Branch branch = branchRepository.findById(id).orElseThrow(
                () -> new CustomException("Id does not exists"));

        return mapper.toDTO(branch);
    }

    @Override
    public List<BranchResDTO> findAll() {
        List<Branch> branches = branchRepository.findByStatusNot(3);
        return mapper.toDTOList(branches);
    }


    @Override
    public CustomPageResponse<BranchResDTO> findAllPaged(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Branch> pagedResult = branchRepository.findByStatusNot(3,pageable);
        List<BranchResDTO> content = mapper.toDTOList(pagedResult.getContent());
        return new CustomPageResponse<>(
                content,
                pagedResult.getSort().isUnsorted(),   // boolean unsorted
                pagedResult.getSort().isSorted(),     // boolean sorted
                pagedResult.getTotalElements(),       // long totalElements
                size,                                 // int size
                page + 1                              // int page (1-based)
        );
    }

    @Override
    public void softDeleteById(Integer id) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new CustomException("Branch Id does not exist"));
        branch.setStatus(3);
        branchRepository.save(branch);
    }

    @Override
    public BranchResDTO update(Integer id,BranchReqDTO branchReqDTO) {
        System.out.println(id);
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new CustomException("Branch Id does not exists!"));
        auditUtil.updateAudit(branchReqDTO);
        if (branchReqDTO.getName() != null){
            branch.setName(branchReqDTO.getName());
            System.out.println(branchReqDTO.getName());
        }
        if (branchReqDTO.getEmail() != null){
            branch.setEmail(branchReqDTO.getEmail());
        }
        if (branchReqDTO.getNumber() != null){
            branch.setNumber(branchReqDTO.getNumber());
        }
        if (branchReqDTO.getUpdatedBy() != null) {
            branch.setUpdatedBy(branchReqDTO.getUpdatedBy());
        }
        branch.setStatus(2);
        Branch saved = branchRepository.save(branch);
        return mapper.toDTO(saved);

    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new CustomException("Branch Id does not exists"));
        branch.setStatus(status);
        branchRepository.save(branch);
    }

    @Override
    public List<BranchResDTO> findAllBanksCreatedBy(Integer userId) {
        List<Branch> branches = branchRepository.findByCreatedBy(userId);
        return mapper.toDTOList(branches);
    }

    @Override
    public List<BranchResDTO> findAllBanksUpdatedBy(Integer userId) {
        List<Branch> branches = branchRepository.findByUpdatedBy(userId);
        return mapper.toDTOList(branches);
    }
}