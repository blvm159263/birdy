package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository  extends JpaRepository<Shipment, Integer> {

    Optional<Shipment> findByIdAndStatus(Integer id, Boolean status);
}
