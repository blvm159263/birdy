package com.newbies.birdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDTO {

    private Integer id;

    private Integer quantity;

    private Double price;

    private Integer productId;

    private String productName;

    private Integer cartId;
}
