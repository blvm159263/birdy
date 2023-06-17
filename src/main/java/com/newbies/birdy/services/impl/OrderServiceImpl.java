package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.OrderDTO;
import com.newbies.birdy.dto.OrderDetailDTO;
import com.newbies.birdy.dto.OrderManageDTO;
import com.newbies.birdy.entities.*;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.OrderDetailMapper;
import com.newbies.birdy.mapper.OrderMapper;
import com.newbies.birdy.repositories.*;
import com.newbies.birdy.services.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

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
    private final ShopRepository shopRepository;

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
    public Order createParentOrder(OrderDTO orderDTO) {
        Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
        order.setCreateDate(new Date());
        order.setCode(UUID.randomUUID().toString());
        order.setStatus(true);
        order.setState(Enum.valueOf(OrderState.class, "PENDING"));
        order.setPaymentStatus(Enum.valueOf(PaymentStatus.class, "PENDING"));
        order.setOrder(null);
        Order savedOrder = orderRepository.save(order);
        if (savedOrder != null) {
            return savedOrder;
        }
        return null;
    }

    @Override
    public Order createOtherOrder(OrderDTO orderDTO, Order parentOrder) {
        Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
        order.setCreateDate(new Date());
        order.setStatus(true);
        order.setState(Enum.valueOf(OrderState.class, "PENDING"));
        order.setPaymentStatus(Enum.valueOf(PaymentStatus.class, "PENDING"));
        order.setOrder(parentOrder);
        Order savedOrder = orderRepository.save(order);
        if (savedOrder != null) {
            return savedOrder;
        }
        return null;
    }

    @Override
    public Map<List<OrderManageDTO>, Integer> getAllOrdersByShopId(Integer shopId, Pageable pageable) {
        Map<List<OrderManageDTO>, Integer> pair = new HashMap<>();

        Shop shop = shopRepository.findByIdAndStatus(shopId, true);
        List<Shipment> shipmentList = shipmentRepository.findByShopShipmentAndStatus(shop, true);
        Page<Order> orderList = orderRepository.findByShipmentOrderInAndStatus(shipmentList, true, pageable);

        List<OrderManageDTO> orderManageDTOList = new ArrayList<>();
        for (Order order : orderList.getContent()) {
            OrderManageDTO orderManageDTO = new OrderManageDTO();
            orderManageDTO.setId(order.getId());
            orderManageDTO.setCustomer(order.getPaymentMethod().getUserPaymentMethod().getFullName());

            List<Double> listPrice = order.getOrderDetailList().stream().map(OrderDetail::getPrice).toList();
            Double total = listPrice.stream().reduce(0.0, Double::sum);

            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            orderManageDTO.setTotal(decimalFormat.format(total));

            orderManageDTO.setShipType(order.getShipmentOrder().getShipmentType().getShipmentTypeName());
            orderManageDTO.setPaymentMethod(String.valueOf(order.getPaymentMethod().getPaymentType().getPaymentTypeName()));
            orderManageDTO.setPaymentStatus(String.valueOf(order.getPaymentStatus()));
            orderManageDTO.setState(String.valueOf(order.getState()));

            orderManageDTOList.add(orderManageDTO);
        }
        pair.put(orderManageDTOList, orderList.getTotalPages());
        return pair;
    }

    @Override
    public Map<List<OrderManageDTO>, Integer> getAllOrdersByShopIdAndState(Integer shopId, String state, Pageable pageable) {
        Map<List<OrderManageDTO>, Integer> pair = new HashMap<>();
        OrderState orderState = Enum.valueOf(OrderState.class, state.toUpperCase());

        Shop shop = shopRepository.findByIdAndStatus(shopId, true);
        List<Shipment> shipmentList = shipmentRepository.findByShopShipmentAndStatus(shop, true);
        Page<Order> orderList = orderRepository.findByShipmentOrderInAndStatusAndState(shipmentList, true, orderState, pageable);

        List<OrderManageDTO> orderManageDTOList = new ArrayList<>();
        for (Order order : orderList.getContent()) {
            OrderManageDTO orderManageDTO = new OrderManageDTO();
            orderManageDTO.setId(order.getId());
            orderManageDTO.setCustomer(order.getPaymentMethod().getUserPaymentMethod().getFullName());

            List<Double> listPrice = order.getOrderDetailList().stream().map(OrderDetail::getPrice).toList();
            Double total = listPrice.stream().reduce(0.0, Double::sum);

            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            orderManageDTO.setTotal(decimalFormat.format(total));

            orderManageDTO.setShipType(order.getShipmentOrder().getShipmentType().getShipmentTypeName());
            orderManageDTO.setPaymentMethod(String.valueOf(order.getPaymentMethod().getPaymentType().getPaymentTypeName()));
            orderManageDTO.setPaymentStatus(String.valueOf(order.getPaymentStatus()));
            orderManageDTO.setState(String.valueOf(order.getState()));

            orderManageDTOList.add(orderManageDTO);
        }
        pair.put(orderManageDTOList, orderList.getTotalPages());
        return pair;
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
            if (product.getShopProduct().getId().equals(shopId)) {
                OrderDetail orderDetail = OrderDetailMapper.INSTANCE.toEntity(orderDetailDTO);
                orderDetail.setStatus(true);
                orderDetail.setRating(0);
                orderDetail.setOrder(order);
                orderDetailRepository.save(orderDetail);
            }
        }


    }

}
