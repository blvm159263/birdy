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

    private Integer shipmentId;

    private String shipmentTypeName;

    private Integer paymentMethodId;

    private String paymentTypeName;

    private Integer orderParentId;

    private Integer paymentStatusId;

    private String paymentStatusName;
}
