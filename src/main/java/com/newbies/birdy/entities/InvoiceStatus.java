package com.newbies.birdy.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_invoice_status")
public class InvoiceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "invoice_status_name", nullable = false)
    private String invoiceStatusName;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "invoiceStatus", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Invoice> invoiceList;
}

