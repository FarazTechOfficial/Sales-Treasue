package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.ProductReqDTO;
import com.example.distribution_sales_techfira.dto.ProductResDTO;
import com.example.distribution_sales_techfira.entity.Product;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product toEntity(ProductReqDTO dto){
        if(dto == null)return null;
        Product p=new Product();
        p.setProductCode(dto.getProductCode());
        p.setCreatedBy(dto.getCreatedBy());
        p.setUpdatedBy(dto.getUpdatedBy());
        p.setId(dto.getId());
        p.setProductDescription(dto.getProductDescription());
        p.setUnitOfMeasurement(dto.getUnitOfMeasurement());
        p.setDistributorPrice(dto.getDistributorPrice());
        p.setTradePrice(dto.getTradePrice());
        p.setMaximumRetailPrice(dto.getMaximumRetailPrice());
        p.setQuantityOpInPieces(dto.getQuantityOpInPieces());
        p.setQuantityOpInCarton(dto.getQuantityOpInCarton());
        p.setStatus(dto.getStatus() != null ? dto.getStatus() : 2);
        return p;
    }
    public ProductResDTO toDTO(Product product){
        if(product == null)return null;
        ProductResDTO resDTO=new ProductResDTO();
        resDTO.setUpdatedBy(product.getUpdatedBy());
        resDTO.setUpdatedAt(product.getUpdatedAt());
        resDTO.setCreatedAt(product.getCreatedAt());
        resDTO.setCreatedBy(product.getCreatedBy());
        resDTO.setId(product.getId());
        resDTO.setProductCode(product.getProductCode());
        resDTO.setUnitOfMeasurement(product.getUnitOfMeasurement());
        resDTO.setProductDescription(product.getProductDescription());
        resDTO.setDistributorPrice(product.getDistributorPrice());
        resDTO.setTradePrice(product.getTradePrice());
        resDTO.setMaximumRetailPrice(product.getMaximumRetailPrice());
        resDTO.setQuantityOpInPieces(product.getQuantityOpInPieces());
        resDTO.setQuantityOpInCarton(product.getQuantityOpInCarton());
        resDTO.setStatus(product.getStatus());


        return resDTO;
    }
    public  List<ProductResDTO> toDTOList(List<Product> content){
        return content.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
