package com.example.distribution_sales_techfira.mapper;

import com.example.distribution_sales_techfira.dto.NoteReqDTO;
import com.example.distribution_sales_techfira.dto.NoteResDTO;
import com.example.distribution_sales_techfira.entity.Note;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class NoteMapper {

    public static Note toEntity(NoteReqDTO dto) {
        if (dto == null) return null;

        Note note = new Note();
        note.setCreatedBy(dto.getCreatedBy());
        note.setUpdatedBy(dto.getUpdatedBy());
        note.setNoteName(dto.getNoteName());
        note.setNoteType(dto.getNoteType());
        note.setCode(dto.getCode());
        note.setCreatedAt(dto.getCreatedAt());
        note.setUpdatedAt(dto.getUpdatedAt());
        note.setCreatedBy(dto.getCreatedBy());
        note.setUpdatedBy(dto.getUpdatedBy());
        note.setStatus(dto.getStatus() != null ? dto.getStatus() : 2);

        return note;
    }

    public static NoteResDTO toDTO(Note note) {
        if (note == null) return null;

        NoteResDTO dto = new NoteResDTO();
        dto.setId(note.getId());
        dto.setNoteName(note.getNoteName());
        dto.setNoteType(note.getNoteType());
        dto.setCode(note.getCode());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setUpdatedAt(note.getUpdatedAt());
        dto.setCreatedBy(note.getCreatedBy());
        dto.setUpdatedBy(note.getUpdatedBy());
        dto.setStatus(note.getStatus());

        return dto;
    }

    public static List<NoteResDTO> toDTOList(List<Note> notes) {
        return notes.stream()
                .map(NoteMapper::toDTO)
                .collect(Collectors.toList());
    }
}
