package com.newbies.birdy.services;

import com.newbies.birdy.dto.OrderDTO;
import com.newbies.birdy.dto.OrderDetailDTO;
import com.newbies.birdy.entities.Order;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrdersByUserIdAndStatus(Integer userId, Boolean status);

    String createOrder(List<OrderDTO> orderDTOList, List<OrderDetailDTO> orderDetail);

    Order createParentOrder(OrderDTO orderDTO);

    void saveDetailforOrder(Order order, List<OrderDetailDTO> orderDetailDTOList);

    Order createOtherOrder(OrderDTO orderDTO, Order parentOrder);
}
