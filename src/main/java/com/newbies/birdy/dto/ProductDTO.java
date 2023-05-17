package com.newbies.birdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer id;

    private String productName;

    private Double unitPrice;

    private int salePtc;

    private Integer quantity;

    private Integer rating;

    private Date createDate;

    private Integer state;

    private Boolean status;

    private Integer categoryId;

    private String categoryName;

    private Integer shopId;

    private String shopName;

}
