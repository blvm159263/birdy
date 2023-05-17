package com.newbies.birdy.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    @JsonManagedReference
    @JoinColumn(name = "user_id", nullable = false)
    private User userOrder;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shopOrder;
}
