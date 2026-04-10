package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.MenuReqDTO;
import com.example.distribution_sales_techfira.dto.MenuResDTO;
import com.example.distribution_sales_techfira.entity.Menu;
import com.example.distribution_sales_techfira.entity.Role;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.MenuMapper;
import com.example.distribution_sales_techfira.repository.MenuRepository;
import com.example.distribution_sales_techfira.service.MenuService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImp implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper mapper ;
    private final AuditUtil auditUtil;
    public MenuServiceImp(MenuRepository menuRepository, MenuMapper mapper, AuditUtil auditUtil) {
        this.menuRepository = menuRepository;
        this.mapper = mapper;
        this.auditUtil = auditUtil;
    }

    @Override
    public MenuResDTO save(MenuReqDTO menuReqDTO) {
        Menu menuByName = menuRepository.findByName(menuReqDTO.getName());
        if (menuByName != null){
            throw new CustomException("Menu: " + menuByName.getName() + " already exists");
        }
        auditUtil.createAudit(menuReqDTO);
        Menu saved = menuRepository.save(mapper.toEntity(menuReqDTO));
        return mapper.toDTO(saved);
    }

    @Override
    public MenuResDTO getSingleMenu(Integer id) {
        Menu menuById = menuRepository.findById(id).orElseThrow(() -> new CustomException("Menu Not found by Id"));
        return mapper.toDTO(menuById);
    }

    @Override
    public List<MenuResDTO> getAllMenusWithoutPagination() {
        return mapper.toListDTO(menuRepository.findAllMenu());
    }

    @Override
    public CustomPageResponse<MenuResDTO> getAllMenusWithPagination(int page, int size) {
       Pageable pageable =  PageRequest.of(page,size);
        Page<Menu> pageResult = menuRepository.findAllActivePages(pageable);
        List<MenuResDTO> content = mapper.toListDTO(pageResult.getContent());

        return new CustomPageResponse<MenuResDTO>(
                content,
                pageResult.getSort().isUnsorted(),
                pageResult.getSort().isSorted(),
                pageResult.getTotalElements(),
                size,
                page + 1
        );
    }

    @Override
    public MenuResDTO update(Integer id, MenuReqDTO menuReqDTO) {
        Menu menuById = menuRepository.findById(id).orElseThrow(() -> new CustomException("Menu ID does not exists"));
        auditUtil.updateAudit(menuReqDTO);
        if (menuReqDTO.getName() != null){
            menuById.setName(menuReqDTO.getName());
        }
        if (menuReqDTO.getPath() != null){
            menuById.setPath(menuReqDTO.getPath());
        }
        if (menuReqDTO.getStatus() != null){
            menuById.setStatus(menuReqDTO.getStatus());
        }
        Menu saved = menuRepository.save(menuById);
        return mapper.toDTO(saved);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        Menu menuById = menuRepository.findById(id).orElseThrow(() -> new CustomException("Menu ID does not exists"));
        menuById.setStatus(status);
        Menu saved = menuRepository.save(menuById);

    }

    @Override
    public void softDeleteById(Integer id) {
        Menu menuById = menuRepository.findById(id).orElseThrow(() -> new CustomException("Menu ID does not exists"));
        menuById.setStatus(3);
        Menu saved = menuRepository.save(menuById);

    }
}
