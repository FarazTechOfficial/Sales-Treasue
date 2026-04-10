package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.entity.License;
import com.example.distribution_sales_techfira.entity.Note;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.NoteMapper;


import com.example.distribution_sales_techfira.repository.NotesRepository;
import com.example.distribution_sales_techfira.service.NotesService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesServiceImp implements NotesService {


    private final NoteMapper mapper;
    private final NotesRepository notesRepository;
    
    private final AuditUtil auditUtil;
    public NotesServiceImp(NotesRepository notesRepository, NoteMapper mapper, AuditUtil auditUtil) {
        this.notesRepository = notesRepository;
        this.mapper = mapper;
        this.auditUtil = auditUtil;
    }

    @Override
    public String generateNextCode(String noteType) {
        String prefix = noteType.equalsIgnoreCase("credit") ? "NTC" : "NTD";
        long count = notesRepository.countByNoteType(noteType) + 1;
        return String.format("%s%02d", prefix, count);
    }
    @Override
    public NoteResDTO save(NoteReqDTO noteReqDTO) {
        auditUtil.createAudit(noteReqDTO);
        Note entity = mapper.toEntity(noteReqDTO);
        entity.setStatus(2);
        Note saved = notesRepository.save(entity);

        return mapper.toDTO(saved);
    }
    @Override
    public NoteResDTO findByID(Integer id) {
        Note note = notesRepository.findById(id).orElseThrow (
                () -> new CustomException("No such ID present"));
        return mapper.toDTO(note);
    }
    @Override
    public List<NoteResDTO> findAll() {

        List<Note> notes = notesRepository.findByStatusNot(3);
        return NoteMapper.toDTOList(notes);
    }
    @Override
    public void softDeleteById(Integer id) {
        Note note = notesRepository.findById(id).orElseThrow(() -> new CustomException("Note Id does not exist!"));
        note.setStatus(3);
        notesRepository.save(note);
    }
    @Override
    public NoteResDTO update(Integer id, NoteReqDTO noteReqDTO) {
        System.out.println("update call");
        Note note = notesRepository.findById(id).orElseThrow (
                () -> new CustomException("Note ID does not exists"));
        auditUtil.updateAudit(noteReqDTO);
        if (noteReqDTO.getNoteName() != null) {
            note.setNoteName(noteReqDTO.getNoteName());
        }
        if (noteReqDTO.getNoteType() != null) {
            note.setNoteType(noteReqDTO.getNoteType());
        }
        if (noteReqDTO.getCode() != null) {
            note.setCode(noteReqDTO.getCode());
        }
        if (noteReqDTO.getUpdatedBy() != null) {
            note.setUpdatedBy(noteReqDTO.getUpdatedBy());
        }

        note.setStatus(2);
        Note saved = notesRepository.save(note);
        return mapper.toDTO(saved);
    }

//    @Override
//    public void updateStatus(Integer id, Integer status) {
//
//    }
//
//    @Override
//    public CustomPageResponse<NoteResDTO> findAllPaged(int page, int size) {
//        return null;
//    }

    @Override
    public List<NoteResDTO> findByType(String noteType) {
        List<Note> notes = notesRepository.findByNoteTypeIgnoreCase(noteType);
        return mapper.toDTOList(notes);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        Note note = notesRepository.findById(id).orElseThrow(() -> new CustomException("Bank Id does not exists"));
        note.setStatus(status);
        notesRepository.save(note);
    }

    @Override
    public CustomPageResponse<NoteResDTO> findAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Get paged result from the repository
        Page<Note> pagedResult = notesRepository.findByStatusNot(3,pageable);

        // Convert the content of the page to DTOs
        List<NoteResDTO> content = mapper.toDTOList(pagedResult.getContent());

        // Return CustomPageResponse with the correct arguments
        return new CustomPageResponse<NoteResDTO>(
                content, // List of DTOs
                pagedResult.getSort().isUnsorted(), // Unsorted flag
                pagedResult.getSort().isSorted(),   // Sorted flag
                pagedResult.getTotalElements(),     // Total number of elements
                size,                               // Page size
                page + 1                           // Page number (1-based)
        );
    }

    @Override
    public List<NoteResDTO> findAllDebitNotes() {
        List<Note> allDebitNotes = notesRepository.findByNoteTypeIgnoreCaseAndStatusNot("Debit", 3);

        return mapper.toDTOList(allDebitNotes);
    }

    @Override
    public List<NoteResDTO> findAllCreditNotes() {
        List<Note> allCreditNotes = notesRepository.findByNoteTypeIgnoreCaseAndStatusNot("Credit", 3);
        return mapper.toDTOList(allCreditNotes);
    }

    @Override
    public List<NoteResDTO> findAllBanksCreatedBy(Integer userId) {
        List<Note> notes = notesRepository.findByCreatedBy(userId);
        return mapper.toDTOList(notes);
    }

    @Override
    public List<NoteResDTO> findAllBanksUpdatedBy(Integer userId) {
        List<Note> notes = notesRepository.findByUpdatedBy(userId);
        return mapper.toDTOList(notes);
    }

}