package com.newbies.birdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Integer id;

    private String code;

    private Date createDate;

    private Integer state;

    private String comment;

    private Boolean status;

    private Integer userId;

    private String fullName;

    private Integer shopId;

    private String shopName;
}
