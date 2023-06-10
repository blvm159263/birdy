package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Order;
import com.newbies.birdy.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByPaymentMethodAndStatus(PaymentMethod paymentMethod, Boolean status);
}
