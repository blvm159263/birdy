package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByPaymentMethodAndStatus(PaymentMethod paymentMethod, Boolean status);

    List<Order> findByCodeAndStatus(String code, Boolean status);

    Page<Order> findByShipmentOrderInAndStatus(List<Shipment> shipmentList, Boolean status, Pageable pageable);

    Page<Order> findByShipmentOrderInAndStatusAndState(List<Shipment> shipmentList, Boolean status, OrderState state, Pageable pageable);

    Optional<Order> findByIdAndStatus(Integer orderId, Boolean b);

    Page<Order> findByShipmentOrderInAndPaymentMethodInAndStatus(List<Shipment> shipmentList, List<PaymentMethod> paymentMethodList, Boolean status, Pageable pageable);

    Page<Order> findByShipmentOrderInAndPaymentMethodInAndStateAndStatus(List<Shipment> shipmentList, List<PaymentMethod> paymentMethodList, OrderState state, Boolean status, Pageable pageable);

    Page<Order> findByShipmentOrderInAndPaymentMethodInAndPaymentStatusAndStatus(List<Shipment> shipmentList, List<PaymentMethod> paymentMethodList, PaymentStatus paymentStatus, Boolean status, Pageable pageable);

    Page<Order> findByShipmentOrderInAndPaymentMethodInAndPaymentStatusAndStateAndStatus(List<Shipment> shipmentList, List<PaymentMethod> paymentMethodList, PaymentStatus paymentStatus, OrderState state, Boolean status, Pageable pageable);
}
