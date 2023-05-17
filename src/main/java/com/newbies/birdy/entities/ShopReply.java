package com.newbies.birdy.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_shop_reply")
public class ShopReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "shop_id")
    private Shop shopReply;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "review_id")
    private Review reviewShopReply;

}
