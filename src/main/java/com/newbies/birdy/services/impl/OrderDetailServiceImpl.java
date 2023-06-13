package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.OrderDetailDTO;
import com.newbies.birdy.entities.Order;
import com.newbies.birdy.entities.OrderDetail;
import com.newbies.birdy.mapper.OrderDetailMapper;
import com.newbies.birdy.repositories.OrderDetailRepository;
import com.newbies.birdy.services.OrderDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetailDTO> getOrderDetailsByOrderIdAndStatus(Integer orderId, Boolean status) {
        Order Order = new Order();
        Order.setId(orderId);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderAndStatus(Order, status);
        return orderDetailList.stream().map(OrderDetailMapper.INSTANCE::toDTO).toList();
    }
}
