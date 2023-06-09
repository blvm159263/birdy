package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findByUserAddressAndStatus(Integer userId, Boolean status);
}
