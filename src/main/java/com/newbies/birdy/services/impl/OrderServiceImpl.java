package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.OrderDTO;
import com.newbies.birdy.entities.Order;
import com.newbies.birdy.entities.PaymentMethod;
import com.newbies.birdy.entities.User;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.OrderMapper;
import com.newbies.birdy.repositories.OrderRepository;
import com.newbies.birdy.repositories.PaymentMethodRepository;
import com.newbies.birdy.repositories.UserRepository;
import com.newbies.birdy.services.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final UserRepository userRepository;


    @Override
    public List<OrderDTO> getAllOrdersByUserIdAndStatus(Integer userId, Boolean status) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findByUserPaymentMethodAndStatus(user, status);
        List<Order> orderList = new ArrayList<>();
        for (PaymentMethod paymentMethod : paymentMethodList) {
            orderList.addAll(orderRepository.findByPaymentMethodAndStatus(paymentMethod, status));
        }
        return orderList.stream().map(OrderMapper.INSTANCE::toDTO).toList();
    }
}
