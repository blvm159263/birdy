package com.newbies.birdy.services;

import com.newbies.birdy.dto.OrderDTO;
import com.newbies.birdy.dto.OrderDetailDTO;
import com.newbies.birdy.dto.OrderManageDTO;
import com.newbies.birdy.entities.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface OrderService {

    List<OrderDTO> getAllOrdersByUserIdAndStatus(Integer userId, Boolean status);

    String createOrder(List<OrderDTO> orderDTOList, List<OrderDetailDTO> orderDetail);

    Order createParentOrder(OrderDTO orderDTO);

    void saveDetailforOrder(Order order, List<OrderDetailDTO> orderDetailDTOList);

    Order createOtherOrder(OrderDTO orderDTO, Order parentOrder);

    Map<List<OrderManageDTO>, Long> getAllOrdersByShopId(Integer shopId, String search, List<String> payment, List<String> state, Pageable pageable);

//    Map<List<OrderManageDTO>, Long> getAllOrderByShop(Integer shopId, String search, Pageable pageable);

    List<OrderManageDTO> getAllOrderByShop(Integer shopId, String search);

    Map<List<OrderManageDTO>, Integer> getAllOrdersByShopIdAndState(Integer shopId, String state, Pageable pageable);

    Boolean editOrderState(Integer orderId, String state);
}
