package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Order;
import com.newbies.birdy.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findByOrderAndStatus(Order order, Boolean status);
}
