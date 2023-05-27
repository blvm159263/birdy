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
@Table(name = "tbl_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "state", nullable = false)
    private Integer state;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    @JsonManagedReference
    private Shipment shipmentOrder;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id", nullable = false)
    private User userOrder;

    @OneToMany(mappedBy = "orderStatusDetail", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OrderStatusDetail> orderStatusDetailList;

    @OneToMany(mappedBy = "orderInvoice", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Invoice> invoiceList;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OrderDetail> orderDetailList;


}
