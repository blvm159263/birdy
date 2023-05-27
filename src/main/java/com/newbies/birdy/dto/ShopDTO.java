package com.newbies.birdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDTO {

    private Integer id;

    private String phoneNumber;

    private String password;

    private String shopName;

    private String address;

    private String avatarUrl;

    private Date createDate;

    private Boolean status;

}
