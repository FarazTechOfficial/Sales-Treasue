package com.example.distribution_sales_techfira.controller;

import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.service.NotesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("note")
public class NotesController extends BaseController<NoteReqDTO,NoteResDTO,Integer> {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        super(notesService);
        this.notesService = notesService;
    }
    @Override
    protected String getEntityName() {
        return "note";
    }


    @GetMapping("/generate-code")
    public ResponseEntity<String> generateNextCode(@RequestParam String noteType) {
        return ResponseEntity.ok(notesService.generateNextCode(noteType));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<NoteResDTO>>> getAll() {
        List<NoteResDTO> result = notesService.findAll();
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }


    @GetMapping("/debit")
    public ResponseEntity<BaseResponse<List<NoteResDTO>>> getAllDebit() {
        List<NoteResDTO> result = notesService.findAllDebitNotes();
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }

    @GetMapping("credit")
    public ResponseEntity<BaseResponse<List<NoteResDTO>>> getAllCredit() {
        List<NoteResDTO> result = notesService.findAllCreditNotes();
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }


    @GetMapping("/notes")
    public ResponseEntity<BaseResponse<CustomPageResponse<NoteResDTO>>> getAllWithPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int zeroBasedPage = Math.max(page - 1, 0);
        CustomPageResponse<NoteResDTO> result = notesService.findAllPaged(zeroBasedPage, size);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }
    @GetMapping("/by-type")
    public ResponseEntity<BaseResponse<List<NoteResDTO>>> getByType(@RequestParam String type) {
        List<NoteResDTO> result = notesService.findByType(type);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }

}
