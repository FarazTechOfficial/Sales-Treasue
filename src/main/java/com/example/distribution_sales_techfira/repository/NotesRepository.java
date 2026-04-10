package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.Bank;
import com.example.distribution_sales_techfira.entity.Branch;
import com.example.distribution_sales_techfira.entity.License;
import com.example.distribution_sales_techfira.entity.Note;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotesRepository extends JpaRepository<Note, Integer> {
    long countByNoteType(String noteType); // Code generate karega

    List<Note> findByStatusNot(Integer status);
    Page<Note> findByStatusNot(Integer status, Pageable pageable);
    @Query(value = "SELECT * FROM note WHERE note_type = 'Credit' AND status != 3 AND note_name = :name LIMIT 1", nativeQuery = true)
    Note findAllCreditNotes(@Param( "name") String noteName);
    @Query(value = "SELECT * FROM note WHERE note_type = 'Debit' AND status != 3 AND note_name = :name LIMIT 1", nativeQuery = true)
    Note findAllDebitNotes(@Param("name") String noteName);
    List<Note> findByNoteTypeIgnoreCaseAndStatusNot(String noteType, Integer status);


    Optional<Note> findByIdAndNoteType(Integer id,String noteType);
    Optional<Note> findByNoteNameIgnoreCase(String noteName);

    Optional<Note> findByNoteNameIgnoreCaseAndNoteType(String creditNoteName, String credit);
    List<Note> findByCreatedBy(Integer userId);
    List<Note> findByUpdatedBy(Integer userId);
    List<Note> findByStatus(Integer status);
    List<Note> findByNoteTypeIgnoreCase(String noteType);
}
