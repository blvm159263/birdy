package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Order;
import com.newbies.birdy.entities.OrderDetail;
import com.newbies.birdy.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findByOrderAndStatus(Order order, Boolean status);

//    @Query("SELECT od FROM OrderDetail od WHERE od.productOrderDetail = ?1 AND od.status = ?2 AND od.rating > 0")
    Page<OrderDetail> findByProductOrderDetailAndStatusAndRatingGreaterThan(Product p, Boolean status, Integer rating, Pageable pageable);
}
