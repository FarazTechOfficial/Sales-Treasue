package com.example.distribution_sales_techfira.repository;

import com.example.distribution_sales_techfira.entity.JournalVoucherNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalVoucherNoteRepository extends JpaRepository<JournalVoucherNote, Integer> {

  List<JournalVoucherNote> findByJournalVoucherId(Integer id);
   void deleteByJournalVoucherId(Integer id);


}
