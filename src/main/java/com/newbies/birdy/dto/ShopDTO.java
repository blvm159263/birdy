package com.newbies.birdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDTO {

    private Integer id;

    private String shopName;

    private String address;

    private String avatarUrl;

    private Boolean status;

    private Integer userId;

}
