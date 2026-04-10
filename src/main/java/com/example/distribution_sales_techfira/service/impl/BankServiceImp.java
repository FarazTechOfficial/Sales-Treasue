package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.entity.Bank;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.BankMapper;
import com.example.distribution_sales_techfira.repository.BankRepository;
import com.example.distribution_sales_techfira.service.BankService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImp implements BankService {
    private final BankRepository bankRepository;
    private  final UserServiceImp userServiceImp;
    private  final AuditUtil auditUtil;
    private BankMapper mapper;
    public BankServiceImp(BankRepository bankRepository, UserServiceImp userServiceImp, AuditUtil auditUtil){
        this.bankRepository = bankRepository;
        this.userServiceImp = userServiceImp;
        this.auditUtil = auditUtil;
    }
    @Override
    public BankResDTO save(BankReqDTO bankReqDTO) {
        auditUtil.createAudit(bankReqDTO);
        Bank entity = mapper.toEntity(bankReqDTO);
        Bank saved = bankRepository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public BankResDTO findByID(Integer id) {
        Bank bank = bankRepository.findById(id).orElseThrow (
                () -> new CustomException("No such ID present"));

        return mapper.toDTO(bank);
    }

    @Override
    public List<BankResDTO> findAll() {
        List<Bank> banks = bankRepository.findByStatusNot(3);
        return mapper.toDTOList(banks);
    }

    @Override
    public void softDeleteById(Integer id) {
       Bank bank = bankRepository.findById(id).orElseThrow(() -> new CustomException("Bank Id does not exist!"));
       bank.setStatus(3);
       bankRepository.save(bank);

    }

    @Override
    public BankResDTO update(Integer id, BankReqDTO bankReqDTO) {
        System.out.println("update call");
        Bank bank = bankRepository.findById(id).orElseThrow (
                () -> new CustomException("Bank ID does not exists"));
        auditUtil.updateAudit(bankReqDTO);
        if (bankReqDTO.getBankName() != null){
            bank.setBankName(bankReqDTO.getBankName());
        }
        if (bankReqDTO.getCountryName() != null){
            bank.setCountryName(bankReqDTO.getCountryName());
        }
        if (bankReqDTO.getUpdatedBy() != null) {
            bank.setUpdatedBy(bankReqDTO.getUpdatedBy());
        }
        bank.setStatus(2);
        Bank saved = bankRepository.save(bank);
        return mapper.toDTO(saved);
    }
    @Override
    public void updateStatus(Integer id, Integer status) {
        Bank bank = bankRepository.findById(id).orElseThrow(() -> new CustomException("Bank Id does not exists"));
         bank.setStatus(status);
         bankRepository.save(bank);
    }
    @Override
    public CustomPageResponse<BankResDTO> findAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bank> pagedResult = bankRepository.findByStatusNot(3,pageable);
        List<BankResDTO> content = mapper.toDTOList(pagedResult.getContent());
        return new CustomPageResponse<BankResDTO>(
                content,
                pagedResult.getSort().isUnsorted(),
                pagedResult.getSort().isSorted(),
                pagedResult.getTotalElements(),
                size,
                page + 1
        );
    }
    @Override
    public List<BankResDTO> findAllBanksCreatedBy(Integer userId) {
        List<Bank> banks = bankRepository.findByCreatedBy(userId);
        return mapper.toDTOList(banks);
    }

    @Override
    public List<BankResDTO> findAllBanksUpdatedBy(Integer userId) {
        List<Bank> banks = bankRepository.findByUpdatedBy(userId);
        return mapper.toDTOList(banks);
    }
}
