package com.newbies.birdy.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "invoice_date", nullable = false)
    private Date invoiceDate;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    private Order orderInvoice;

    @ManyToOne
    @JoinColumn(name = "invoice_status_id")
    @JsonManagedReference
    private InvoiceStatus invoiceStatus;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    @JsonManagedReference
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JsonManagedReference
    private Shop shopInvoice;

    @ManyToOne
    @JoinColumn(name = "invoice_parent_id")
    @JsonManagedReference
    private Invoice invoice;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Invoice> invoiceList;


}
