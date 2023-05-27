package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository  extends JpaRepository<Shipment, Integer> {
}
