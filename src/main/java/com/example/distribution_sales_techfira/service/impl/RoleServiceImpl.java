package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.entity.Privilege;
import com.example.distribution_sales_techfira.entity.Role;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.RoleMapper;
import com.example.distribution_sales_techfira.repository.PrivilegeRepository;
import com.example.distribution_sales_techfira.repository.RoleRepository;
import com.example.distribution_sales_techfira.repository.UserRepository;
import com.example.distribution_sales_techfira.service.RoleService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PrivilegeRepository privilegeRepository;
    private final AuditUtil auditUtil;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository, PrivilegeRepository privilegeRepository, AuditUtil auditUtil) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.privilegeRepository = privilegeRepository;
        this.auditUtil = auditUtil;
    }

    @Override
    @Transactional
    public RoleResDTO save(RoleReqDTO roleReqDTO) {
        // Check for duplicate role
        Role roleByName = roleRepository.findByName(roleReqDTO.getName());
        auditUtil.createAudit(roleReqDTO);
        if (roleByName != null){
            throw new CustomException("Role: " + roleByName.getName() + " already exists");
        }

        List<Privilege> privileges = getPrivilegesFromIds(roleReqDTO.getPrivileges());


        Role role = new Role();
        role.setName(roleReqDTO.getName());
        role.setPrivileges(privileges);

        Role saved = roleRepository.save(role);
        return RoleMapper.toDTO(saved);
    }


    @Override
    public List<RoleResDTO> getAllRoleWithoutPagination() {
        List<Role> all = roleRepository.findAll();
        return RoleMapper.toListDTO(all);
    }

    @Override
    public RoleResDTO getSinglePrivilege(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new CustomException("Role Not Found By ID"));
        return RoleMapper.toDTO(role);
    }

    @Override
    public CustomPageResponse<RoleResDTO> getAllRoleWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> pages = roleRepository.ActiveRolePages(pageable);
        List<RoleResDTO> listDTO = RoleMapper.toListDTO(pages.getContent());

        return new CustomPageResponse<>(
                listDTO,
                pages.getSort().isUnsorted(),
                pages.getSort().isSorted(),
                pages.getTotalElements(),
                page,
                size
        );
    }

    @Override
    @Transactional
    public RoleResDTO update(Integer id, RoleReqDTO roleReqDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new CustomException("Role Not Found By ID"));
        auditUtil.updateAudit(roleReqDTO);
        if (roleReqDTO.getName() != null) {
            role.setName(roleReqDTO.getName());
        }

        if (roleReqDTO.getPrivileges() != null && !roleReqDTO.getPrivileges().isEmpty()) {
            List<Privilege> privileges = getPrivilegesFromIds(roleReqDTO.getPrivileges());
            role.setPrivileges(privileges);
        }
        Role saved = roleRepository.save(role);
        return RoleMapper.toDTO(saved);
    }

    @Override
    public RoleResDTO updateStatus(Integer id, Integer status) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new CustomException("Role Not Found By ID"));
        return RoleMapper.toDTO(role);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new CustomException("Role Not Found By ID"));
        long userCount = userRepository.findAll().stream()
                .filter(user -> user.getRole() != null && user.getRole().getId().equals(id))
                .count();

        if (userCount > 0) {
            throw new CustomException("Cannot delete role because it's assigned to " + userCount + " user(s)");
        }
        role.setStatus(3);
        roleRepository.save(role);
    }

    // ✅ Helper method to fetch privileges from DTOs
    private List<Privilege> getPrivilegesFromIds(List<PrivilegeReqDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            throw new CustomException("Privileges list must not be null or empty");
        }

        List<Privilege> privileges = new ArrayList<>();
        for (PrivilegeReqDTO dto : dtoList) {
            Privilege privilege = privilegeRepository.findById(dto.getId())
                    .orElseThrow(() -> new CustomException("Privilege Not Found with ID: " + dto.getId()));
            privileges.add(privilege);
        }
        return privileges;
    }

}