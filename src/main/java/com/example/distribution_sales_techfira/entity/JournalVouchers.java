package com.example.distribution_sales_techfira.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "journal_vouchers")
@Getter
@Setter
@NoArgsConstructor
public class JournalVouchers extends BaseEntity{


    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @OneToMany(mappedBy = "journalVoucher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalVoucherNote> journalVoucherNotes = new ArrayList<>();

    public JournalVouchers(LocalDate transactionDate, String description, Company company, List<JournalVoucherNote> journalVoucherNotes) {
        this.transactionDate = transactionDate;
        this.description = description;
        this.company = company;
        this.journalVoucherNotes = journalVoucherNotes;
    }


}
