package com.newbies.birdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private Integer id;

    private Date invoiceDate;

    private Boolean status;

    private Integer orderId;

    private Integer invoiceStatusId;

    private String invoiceStatusName;

    private Integer paymentMethodId;

    private String paymentTypeName;

    private Integer shopId;

    private String shopName;

    private Integer invoiceParentId;


}
