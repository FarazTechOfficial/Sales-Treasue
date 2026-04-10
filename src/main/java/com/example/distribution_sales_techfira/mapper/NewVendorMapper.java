package com.example.distribution_sales_techfira.mapper;



import com.example.distribution_sales_techfira.dto.VendorReqDTO;
import com.example.distribution_sales_techfira.dto.VendorResDTO;
import com.example.distribution_sales_techfira.entity.Bank;
import com.example.distribution_sales_techfira.entity.Vendor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewVendorMapper {

    public Vendor toEntity(VendorReqDTO dto) {
        if (dto == null) return null;

        Vendor vendor = new Vendor();
        vendor.setVendorName(dto.getVendorName());
        vendor.setEmailAddress(dto.getEmailAddress());
        vendor.setContactNumber(dto.getContactNumber());
        vendor.setAddress(dto.getAddress());
        vendor.setAccountNumber(dto.getAccountNumber());

        Bank bank = dto.getBank();
        if (bank != null) {
            vendor.setBank(bank); // assuming dto.getBank() gives a Bank object with ID set
        }

        vendor.setStatus(dto.getStatus());
        return vendor;
    }

    public VendorResDTO toDTO(Vendor vendor) {
        if (vendor == null) return null;

        VendorResDTO dto = new VendorResDTO();
        dto.setId(vendor.getId());
        dto.setVendorName(vendor.getVendorName());
        dto.setEmailAddress(vendor.getEmailAddress());
        dto.setContactNumber(vendor.getContactNumber());
        dto.setAddress(vendor.getAddress());
        dto.setAccountNumber(vendor.getAccountNumber());
        dto.setStatus(vendor.getStatus());
        dto.setVendorCode(vendor.getVendorCode());

        Bank bank = vendor.getBank();
        if (bank != null) {
            dto.setBank(bank); // or set only bankName/id if your DTO has partial fields
        }

        return dto;
    }

    public List<VendorResDTO> toListDTO(List<Vendor> vendors) {
        return vendors.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
