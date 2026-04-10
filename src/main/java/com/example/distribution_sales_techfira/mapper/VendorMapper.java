package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.VendorReqDTO;
import com.example.distribution_sales_techfira.dto.VendorResDTO;
import com.example.distribution_sales_techfira.entity.Vendor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class VendorMapper {

    

    public static Vendor toEntity(VendorReqDTO dto) {
        if (dto == null) return null;

        Vendor vendor = new Vendor();
        vendor.setCreatedBy(dto.getCreatedBy());
        vendor.setUpdatedBy(dto.getUpdatedBy());
        vendor.setVendorName(dto.getVendorName());
        vendor.setContactNumber(dto.getContactNumber());
        vendor.setAddress(dto.getAddress());
        vendor.setEmailAddress(dto.getEmailAddress());
        vendor.setAccountNumber(dto.getAccountNumber());
        vendor.setBank(dto.getBank());
        vendor.setStatus(dto.getStatus() != null ? dto.getStatus() : 2);

        return vendor;
    }


    public static VendorResDTO toDTO(Vendor vendor) {
        if (vendor == null) return null;

        VendorResDTO dto = new VendorResDTO();
        dto.setId(vendor.getId());
        dto.setVendorName(vendor.getVendorName());
        dto.setContactNumber(vendor.getContactNumber());
        dto.setAddress(vendor.getAddress());
        dto.setEmailAddress(vendor.getEmailAddress());
        dto.setAccountNumber(vendor.getAccountNumber());
        dto.setBank(vendor.getBank());
        dto.setStatus(vendor.getStatus());
        dto.setVendorCode(vendor.getVendorCode());
        dto.setCreatedAt(vendor.getCreatedAt());
        dto.setCreatedBy(vendor.getCreatedBy());
        dto.setUpdatedAt(vendor.getUpdatedAt());
        dto.setUpdatedBy(vendor.getUpdatedBy());

        return dto;
    }

    public static List<VendorResDTO> toListDTO(List<Vendor> vendors) {
        return vendors.stream()
                .map(VendorMapper::toDTO)
                .collect(Collectors.toList());
    }
}
