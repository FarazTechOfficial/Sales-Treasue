package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.JournalVoucherReqDTO;
import com.example.distribution_sales_techfira.dto.JournalVoucherResDTO;
import com.example.distribution_sales_techfira.dto.LicenseResDTO;
import com.example.distribution_sales_techfira.dto.NoteAmountDTO;
import com.example.distribution_sales_techfira.entity.*;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JournalVoucherMapper {
    public JournalVouchers toEntity(JournalVoucherReqDTO dto) {
        if (dto == null) {
            return null;
        }
        JournalVouchers journalVouchers = new JournalVouchers();
        journalVouchers.setTransactionDate(dto.getTransactionDate());
        journalVouchers.setDescription(dto.getDescription());
        journalVouchers.setStatus(dto.getStatus());
        journalVouchers.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        journalVouchers.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        journalVouchers.setCreatedBy(dto.getCreatedBy() );
        journalVouchers.setUpdatedBy(dto.getUpdatedBy() );
        return journalVouchers;
    }
    public static  JournalVoucherResDTO toDTO(JournalVouchers entity) {
        if (entity == null) {
            return null;
        }
        JournalVoucherResDTO journalVoucherResDTO = new JournalVoucherResDTO();
        journalVoucherResDTO.setCompany(CompanyMapper.toDTO(entity.getCompany()));
        journalVoucherResDTO.setStatus(entity.getStatus());
        journalVoucherResDTO.setId(entity.getId());
        journalVoucherResDTO.setTransactionDate(entity.getTransactionDate());
        journalVoucherResDTO.setDescription(entity.getDescription());
        journalVoucherResDTO.setCreatedAt(entity.getCreatedAt());
        journalVoucherResDTO.setUpdatedAt(entity.getUpdatedAt());
        journalVoucherResDTO.setCreatedBy(entity.getCreatedBy());
        journalVoucherResDTO.setUpdatedBy(entity.getUpdatedBy());
        return journalVoucherResDTO;
    }


    // Custom method to map voucher and notes
    public JournalVoucherResDTO toDetailedDTO(JournalVouchers voucher, JournalVoucherNote creditNote, JournalVoucherNote debitNote) {
        JournalVoucherResDTO res = toDTO(voucher);
        res.setCreditNote(toNoteAmountDTO(creditNote));
        res.setDebitNote(toNoteAmountDTO(debitNote));
        return res;
    }

    // Converts JournalVoucherNote to NoteAmountDTO
    public NoteAmountDTO toNoteAmountDTO(JournalVoucherNote note) {
        if (note == null) return null;
        return new NoteAmountDTO(note.getNote().getId(), note.getNote().getNoteName(), note.getAmount());
    }

    // Builds JournalVoucherNote from NoteAmountDTO, Note, and voucher
    public JournalVoucherNote toNoteEntity(NoteAmountDTO dto, JournalVouchers voucher, Note note, String type) {
        JournalVoucherNote entity = new JournalVoucherNote();
        entity.setJournalVoucher(voucher);
        entity.setNote(note);
        entity.setAmount(dto.getAmount());
        entity.setNoteType(type);
        return entity;
    }


    public static List<JournalVoucherResDTO> toDTOList(List<JournalVouchers> vouchers) {
        return vouchers.stream()
                .map(JournalVoucherMapper::toDTO)
                .collect(Collectors.toList());
    }

}
