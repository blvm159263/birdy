package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.OrderDTO;
import com.newbies.birdy.dto.OrderDetailDTO;
import com.newbies.birdy.entities.*;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.OrderDetailMapper;
import com.newbies.birdy.mapper.OrderMapper;
import com.newbies.birdy.repositories.*;
import com.newbies.birdy.services.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final UserRepository userRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final ProductRepository productRepository;

    private final ShipmentRepository shipmentRepository;

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

    @Override
    public Order createParentOrder(OrderDTO orderDTO){
        Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
        order.setCreateDate(new Date());
        order.setCode(UUID.randomUUID().toString());
        order.setStatus(true);
        order.setState(Enum.valueOf(OrderState.class, "PENDING"));
        order.setPaymentStatus(Enum.valueOf(PaymentStatus.class, "PENDING"));
        order.setOrder(null);
        Order savedOrder = orderRepository.save(order);
        if(savedOrder != null){
            return savedOrder;
        }
        return null;
    }

    @Override
    public Order createOtherOrder(OrderDTO orderDTO, Order parentOrder){
        Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
        order.setCreateDate(new Date());
        order.setStatus(true);
        order.setState(Enum.valueOf(OrderState.class, "PENDING"));
        order.setPaymentStatus(Enum.valueOf(PaymentStatus.class, "PENDING"));
        order.setOrder(parentOrder);
        Order savedOrder = orderRepository.save(order);
        if(savedOrder != null){
            return savedOrder;
        }
        return null;
    }
    @Override
    public String createOrder(List<OrderDTO> orderDTOList, List<OrderDetailDTO> orderDetailDTOList) {
        List<Order> orderList = new ArrayList<>(orderDTOList.stream().map(o -> {
            Order order = OrderMapper.INSTANCE.toEntity(o);
            order.setCreateDate(new Date());
            order.setCode(UUID.randomUUID().toString());
            order.setStatus(true);
            order.setState(Enum.valueOf(OrderState.class, "PENDING"));
            order.setPaymentStatus(Enum.valueOf(PaymentStatus.class, "PENDING"));
            order.setOrder(null);
            return order;
        }).toList());

        Order parentOrder = orderRepository.save(orderList.get(0));
        System.out.println(parentOrder);
        if (parentOrder != null) {
            saveDetailforOrder(parentOrder, orderDetailDTOList);
            orderList.remove(0);
            for (Order order : orderList) {
                order.setOrder(parentOrder);
                order.setCode(parentOrder.getCode());
                Order orderSaved = orderRepository.save(order);
                if (orderSaved != null) {
                    saveDetailforOrder(orderSaved, orderDetailDTOList);
                }
            }
            return parentOrder.getCode();
        }

        return null;
    }

    @Override
    public void saveDetailforOrder(Order order, List<OrderDetailDTO> orderDetailDTOList) {
        Integer shopId = shipmentRepository.findById(order.getShipmentOrder().getId()).orElseThrow(() -> new EntityNotFoundException("Shipment not found")).getShopShipment().getId();
        for (OrderDetailDTO orderDetailDTO : orderDetailDTOList) {
            Product product = productRepository.findById(orderDetailDTO.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found!"));
            if(product.getShopProduct().getId().equals(shopId)) {
                OrderDetail orderDetail = OrderDetailMapper.INSTANCE.toEntity(orderDetailDTO);
                orderDetail.setStatus(true);
                orderDetail.setRating(0);
                orderDetail.setOrder(order);
                orderDetailRepository.save(orderDetail);
            }
        }


    }

}
