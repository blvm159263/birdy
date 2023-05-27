package com.newbies.birdy.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "tbl_shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "create_Date", nullable = false)
    private Date createDate;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "shopProduct", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Product> productList;

    @OneToMany(mappedBy = "shopShipment", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Shipment> shipmentList;

    @OneToMany(mappedBy = "shopInvoice", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Invoice> invoiceList;
}
