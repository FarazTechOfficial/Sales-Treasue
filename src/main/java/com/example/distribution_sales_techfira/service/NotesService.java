package com.example.distribution_sales_techfira.service;

import com.example.distribution_sales_techfira.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface NotesService extends BaseService<NoteReqDTO,NoteResDTO,Integer>{
    List<NoteResDTO> findByType(String noteType);
    String generateNextCode(String noteType);
    List<NoteResDTO> findAllDebitNotes();
    List<NoteResDTO> findAllCreditNotes();
    List<NoteResDTO> findAllBanksCreatedBy(Integer userId);
    List<NoteResDTO> findAllBanksUpdatedBy(Integer userId);

}
