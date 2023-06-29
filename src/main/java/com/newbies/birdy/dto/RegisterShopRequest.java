package com.newbies.birdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterShopRequest {

    private String email;

    private String shopName;

    private String address;

    private String phoneNumber;

    private String password;
}
