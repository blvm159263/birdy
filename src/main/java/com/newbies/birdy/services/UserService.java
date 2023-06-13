package com.newbies.birdy.services;

import com.newbies.birdy.dto.AddressDTO;
import com.newbies.birdy.dto.UserDTO;
import com.newbies.birdy.dto.RegisterRequest;
import com.newbies.birdy.entities.Account;

import java.util.List;

public interface UserService {

    public UserDTO getUserById(Integer id);

    UserDTO getUserByAccount(Account account);
}
