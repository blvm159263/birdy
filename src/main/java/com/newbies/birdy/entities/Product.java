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
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "sale_pct")
    private int salePtc;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

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

    @Column(name = "state", nullable = false)
    private Integer state;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shopProduct;

    @OneToMany(mappedBy = "productImg", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<ProductImage> productImageList;

    @OneToMany(mappedBy = "productOrderDetail", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OrderDetail> OrderDetailList;

}
