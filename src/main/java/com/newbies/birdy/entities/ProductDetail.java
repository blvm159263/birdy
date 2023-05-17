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
@Table(name = "tbl_product_detail")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "species", length = 50)
    private String species;

    @Column(name = "age")
    private String age;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "color", length = 20)
    private String color;

    @Column(name = "exp_date")
    private Date expDate;

    @Column(name = "made_in", length = 50)
    private String madeIn;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "size", length = 20)
    private String size;

    @Column(name = "material", length = 20)
    private String material;

    @Column(name = "description")
    private String description;

    @Column(name = "brand_name", length = 20)
    private String brandName;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "product_id", nullable = false)
    private Product productDetail;


}
