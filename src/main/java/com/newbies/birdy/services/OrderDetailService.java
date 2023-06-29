package com.newbies.birdy.services;

import com.newbies.birdy.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailDTO> getOrderDetailsByOrderIdAndStatus(Integer orderId, Boolean status);

    Boolean updateOrderDetails(List<OrderDetailDTO> orderDetailDTOs);
}
