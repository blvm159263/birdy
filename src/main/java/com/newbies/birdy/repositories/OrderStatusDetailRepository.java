package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.OrderStatusDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusDetailRepository extends JpaRepository<OrderStatusDetail, Integer> {
}
