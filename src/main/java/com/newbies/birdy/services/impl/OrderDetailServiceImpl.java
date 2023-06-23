package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.OrderDetailDTO;
import com.newbies.birdy.entities.Order;
import com.newbies.birdy.entities.OrderDetail;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.OrderDetailMapper;
import com.newbies.birdy.repositories.OrderDetailRepository;
import com.newbies.birdy.repositories.OrderRepository;
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
    private final OrderRepository orderRepository;

    @Override
    public List<OrderDetailDTO> getOrderDetailsByOrderIdAndStatus(Integer orderId, Boolean status) {
        Order order = orderRepository.findByIdAndStatus(orderId, status).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderAndStatus(order, status);
        return orderDetailList.stream().map(OrderDetailMapper.INSTANCE::toDTO).toList();
    }
}
