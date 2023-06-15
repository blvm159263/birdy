package com.newbies.birdy.services;

import com.newbies.birdy.dto.AddressDTO;
import com.newbies.birdy.dto.UserDTO;

import java.util.List;

public interface UserService {

    public UserDTO getUserById(Integer id);

    UserDTO getUserByPhoneNumber(String phoneNumber, Boolean status);

    AddressDTO getUserDefaultAddress(Integer id);

    List<AddressDTO> getUserAddressList(Integer id);


}
