package com.newbies.birdy.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "tbl_shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id", nullable = false)
    private User userShop;

    @OneToMany(mappedBy = "shopProduct", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Product> productList;

    @OneToMany(mappedBy = "shopOrder", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Order> orderList;

    @OneToMany(mappedBy = "shopReply", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<ShopReply> shopReplyList;

}
