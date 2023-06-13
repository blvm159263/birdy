package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.AddressDTO;
import com.newbies.birdy.dto.UserDTO;
import com.newbies.birdy.entities.Account;
import com.newbies.birdy.entities.Address;
import com.newbies.birdy.entities.User;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.AddressMapper;
import com.newbies.birdy.mapper.UserMapper;
import com.newbies.birdy.repositories.AccountRepository;
import com.newbies.birdy.repositories.UserRepository;
import com.newbies.birdy.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    @Override
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserMapper.INSTANCE.toDTO(user);
    }

    @Override
    public UserDTO getUserByPhoneNumber(String phoneNumber, Boolean status) {
        Account account = accountRepository
                .findByPhoneNumberAndStatus(phoneNumber, status)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        User user = account.getUser();
        if(user != null)
            return UserMapper.INSTANCE.toDTO(user);
        return null;
    }

    public AddressDTO getUserDefaultAddress(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Address address = user.getAddressList().stream().filter(Address::getIsDefault).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Default address not found"));
        return AddressMapper.INSTANCE.toDTO(address);
    }

}
