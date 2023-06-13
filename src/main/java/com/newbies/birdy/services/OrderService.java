package com.newbies.birdy.services;

import com.newbies.birdy.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrdersByUserIdAndStatus(Integer userId, Boolean status);

}
