package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.PurchaseReturnReqDTO;
import com.example.distribution_sales_techfira.dto.PurchaseReturnResDTO;
import com.example.distribution_sales_techfira.entity.Product;
import com.example.distribution_sales_techfira.entity.PurchaseReturn;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.PurchaseReturnMapper;
import com.example.distribution_sales_techfira.repository.ProductRepository;
import com.example.distribution_sales_techfira.repository.PurchaseReturnRepository;
import com.example.distribution_sales_techfira.service.PurchaseReturnService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import static com.example.distribution_sales_techfira.util.Constants.*;

import com.example.distribution_sales_techfira.util.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

    private final PurchaseReturnRepository purchaseReturnRepository;
    private final PurchaseReturnMapper purchaseReturnMapper;
    private final ProductRepository productRepository;
    private final AuditUtil auditUtil;

    public PurchaseReturnServiceImpl(PurchaseReturnRepository purchaseReturnRepository, PurchaseReturnMapper purchaseReturnMapper, ProductRepository productRepository, AuditUtil auditUtil) {
        this.purchaseReturnRepository = purchaseReturnRepository;
        this.purchaseReturnMapper = purchaseReturnMapper;
        this.productRepository = productRepository;
        this.auditUtil = auditUtil;
    }


    @Override
    public PurchaseReturnResDTO save(PurchaseReturnReqDTO reqDTO) {

        auditUtil.createAudit(reqDTO);

        Product product = productRepository.findByProductCode(reqDTO.getProductCode())
                .orElseThrow(() -> new CustomException(String.format(PRODUCT_NOT_FOUND_BY_CODE, reqDTO.getProductCode())));


        PurchaseReturn purchaseReturn = purchaseReturnMapper.toEntity(reqDTO);
        purchaseReturn.setProduct(product);
        purchaseReturn.setStatus(2);
        purchaseReturn.setPurchaseNumber(generatePurchaseReturnNumber());

        PurchaseReturn savedReturn = purchaseReturnRepository.save(purchaseReturn);

        return purchaseReturnMapper.toDto(savedReturn);
    }


    @Override
    public List<PurchaseReturnResDTO> findAll() {
        return purchaseReturnRepository.findAll().stream()
                .map(purchaseReturnMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseReturnResDTO findByID(Integer id) {
        PurchaseReturn purchaseReturn = purchaseReturnRepository.findById(id)
                .orElseThrow(() -> new CustomException(String.format(PURCHASE_RETURN_NOT_FOUND, id)));
        return purchaseReturnMapper.toDto(purchaseReturn);
    }



    private String generatePurchaseReturnNumber() {
        
        return purchaseReturnRepository.findTopByOrderByCreatedAtDesc()
                .map(lastReturn -> {
                    String purchaseNumber = lastReturn.getPurchaseNumber(); // e.g., "PR-2025-17893"
                    String[] parts = purchaseNumber.split("-");
                    int lastNumber = parts.length == 3 ? Integer.parseInt(parts[2]) : 0001;
                    int newNumber = lastNumber + 1;
                    return String.format(PURCHASE_RETURN_CODE_FORMAT, DateUtil.getCurrentYear(), newNumber);
                })
                .orElse(String.format(PURCHASE_RETURN_CODE_FORMAT, DateUtil.getCurrentYear(),0001));
    }


    @Override
    public CustomPageResponse<PurchaseReturnResDTO> findAllPaged(int page, int size) {
        String sortField = "id";
        Sort sort = Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PurchaseReturn> pagedResult = purchaseReturnRepository.findByStatusNot(3, pageable);
        List<PurchaseReturnResDTO> content = pagedResult.getContent().stream()
                .map(purchaseReturnMapper::toDto)
                .collect(Collectors.toList());

        return new CustomPageResponse<>(
                content,
                pagedResult.getSort().isUnsorted(),
                pagedResult.getSort().isSorted(),
                pagedResult.getTotalElements(),
                size,
                page + 1
        );
    }

    @Override
    public void softDeleteById(Integer id) {
        PurchaseReturn purchaseReturn=purchaseReturnRepository.findById(id)
                .orElseThrow(() -> new CustomException("does not exist"));
        purchaseReturn.setStatus(3);
        purchaseReturnRepository.save(purchaseReturn);
    }

    @Override
    public PurchaseReturnResDTO update(Integer id, PurchaseReturnReqDTO requestDTO) {
        auditUtil.updateAudit(requestDTO);
        PurchaseReturn purchaseReturn=purchaseReturnRepository.findById(id)
                .orElseThrow(() -> new CustomException("does not exist"));
        if(requestDTO.getQuantityInCarton()!= null)purchaseReturn.setQuantityInCarton(requestDTO.getQuantityInCarton());
        if(requestDTO.getQuantityInPieces()!= null)purchaseReturn.setQuantityInPieces(requestDTO.getQuantityInPieces());
        if(requestDTO.getDate()!= null)purchaseReturn.setDate(requestDTO.getDate());
        if(requestDTO.getUnitOfMeasurement()!= null)purchaseReturn.setUnitOfMeasurement(requestDTO.getUnitOfMeasurement());
        purchaseReturn.setStatus(2);
        PurchaseReturn save=purchaseReturnRepository.save(purchaseReturn);
        return purchaseReturnMapper.toDto(save);
    }
}
