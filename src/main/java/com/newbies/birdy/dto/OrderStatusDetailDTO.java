package com.newbies.birdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDetailDTO {

    private Integer id;

    private String comment;

    private Boolean status;

    private Integer orderStatusId;

    private String orderStatusName;

    private String orderId;
}
