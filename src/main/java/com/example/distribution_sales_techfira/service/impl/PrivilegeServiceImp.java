package com.example.distribution_sales_techfira.service.impl;


import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.PrivilegeReqDTO;
import com.example.distribution_sales_techfira.dto.PrivilegeResDTO;
import com.example.distribution_sales_techfira.entity.Menu;
import com.example.distribution_sales_techfira.entity.Privilege;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.MenuMapper;
import com.example.distribution_sales_techfira.mapper.PrivilegeMapper;
import com.example.distribution_sales_techfira.repository.MenuRepository;
import com.example.distribution_sales_techfira.repository.PrivilegeRepository;
import com.example.distribution_sales_techfira.service.PrivilegeService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeServiceImp implements PrivilegeService {


    private final PrivilegeMapper privilegeMapper;
    private final PrivilegeRepository privilegeRepository;
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final AuditUtil auditUtil;

    public PrivilegeServiceImp(PrivilegeRepository privilegeRepository, PrivilegeMapper privilegeMapper, PrivilegeRepository privilegeRepository1, MenuRepository menuRepository, MenuMapper menuMapper, AuditUtil auditUtil) {
        this.privilegeMapper = privilegeMapper;
        this.privilegeRepository = privilegeRepository1;
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
        this.auditUtil = auditUtil;
    }

    @Override
    public PrivilegeResDTO save(PrivilegeReqDTO privilegeReqDTO) {

        Privilege privilegeByName = privilegeRepository.findByName(privilegeReqDTO.getName());
        if (privilegeByName != null){
            throw new CustomException("Privilege: " + privilegeByName.getName() + " already exists");
        }
        Menu menuById = menuRepository.findByIdActive(privilegeReqDTO.getMenu().getId());
        if (menuById.getId() == null){
           throw  new CustomException("Menu Not found By Id");
        }

        auditUtil.createAudit(privilegeReqDTO);
        Privilege entity = privilegeRepository.findByMenu(menuById)
                .orElse(privilegeMapper.toEntity(privilegeReqDTO)); // create new if not exists

        Privilege isPresentPrivilege  = privilegeRepository.findByName(privilegeReqDTO.getName());

        if (isPresentPrivilege !=null ){
           throw  new CustomException("Privilege already exists By Name");
        }

        entity.setName(privilegeReqDTO.getName());
        entity.setStatus(privilegeReqDTO.getStatus());
        entity.setMenu(menuById);

        Privilege saved = privilegeRepository.save(entity);
        return privilegeMapper.toDTO(saved);
    }


    @Override
    public PrivilegeResDTO findByID(Integer id) {
        Privilege privilege = privilegeRepository.findById(id).orElseThrow(() -> new CustomException("Privilege Not Found By ID"));
        return privilegeMapper.toDTO(privilege);
    }

    @Override
    public List<PrivilegeResDTO> getAllPrivilegeWithoutPagination() {
        return privilegeMapper.toListDTO(privilegeRepository.findAllActivePrivileges());
    }

    @Override
    public CustomPageResponse<PrivilegeResDTO> getAllPrivilegeWithPagination(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Privilege> result = privilegeRepository.findAllActivePages(pageRequest);
        List<PrivilegeResDTO> content = privilegeMapper.toListDTO(result.getContent());
        return new CustomPageResponse<PrivilegeResDTO>(
                content,
                result.getSort().isUnsorted(),
                result.getSort().isSorted(),
                result.getTotalElements(),
                page,
                size);
    }

    @Override
    public PrivilegeResDTO update(Integer id, PrivilegeReqDTO privilegeReqDTO) {
        Privilege privilege = privilegeRepository.findById(id).orElseThrow(() -> new CustomException("Privilege Not Found By Id"));
        auditUtil.updateAudit(privilegeReqDTO);
        if (privilegeReqDTO.getName() != null){
            privilege.setName(privilege.getName());
        }
        if (privilege.getStatus() != null){
            privilege.setStatus(privilege.getStatus());
        }
        if (privilege.getMenu() != null){
            Menu byIdActive = menuRepository.findByIdActive(privilegeReqDTO.getMenu().getId());
            if (byIdActive == null){
                throw new CustomException("Menu not found by ID");
            }
            privilege.setMenu(privilegeReqDTO.getMenu());
        }
        Privilege saved = privilegeRepository.save(privilege);
        return privilegeMapper.toDTO(saved);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        Privilege privilege = privilegeRepository.findById(id).orElseThrow(() -> new CustomException("Privilege Not Found By ID"));
        privilege.setStatus(status);
        Privilege saved = privilegeRepository.save(privilege);

    }

    @Override
    public void softDeleteById(Integer id) {
        Privilege privilege = privilegeRepository.findById(id).orElseThrow(() -> new CustomException("Privilege Not Found By ID"));
        privilege.setStatus(3);
    }
}
