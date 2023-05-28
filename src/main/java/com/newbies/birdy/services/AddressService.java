package com.newbies.birdy.services;

import com.newbies.birdy.dto.AddressDTO;

public interface AddressService {

    Integer createAddress(Integer userId, AddressDTO addressDTO);
}
