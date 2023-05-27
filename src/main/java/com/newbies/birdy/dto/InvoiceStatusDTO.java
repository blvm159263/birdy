package com.newbies.birdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceStatusDTO {
    private Integer id;
    private String invoiceStatusName;
    private Boolean status;
}
