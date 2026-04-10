package com.example.distribution_sales_techfira.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "journal_voucher_note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JournalVoucherNote extends BaseEntity{



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_voucher_id", nullable = false)
    private JournalVouchers journalVoucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    private Note note;

    private Double amount;

    @Column(name = "note_type", nullable = false)
    private String noteType;  // "credit" or "debit"
}
