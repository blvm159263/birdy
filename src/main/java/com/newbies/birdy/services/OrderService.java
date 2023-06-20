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

    Map<List<OrderManageDTO>, Integer> getAllOrdersByShopId(Integer shopId, Pageable pageable);

    Map<List<OrderManageDTO>, Integer> getAllOrderByShop(Integer shopId, String search, String state, String paymentStatus, Pageable pageable);

    Map<List<OrderManageDTO>, Integer> getAllOrdersByShopIdAndState(Integer shopId, String state, Pageable pageable);

    Boolean editOrderState(Integer orderId, String state);
}
