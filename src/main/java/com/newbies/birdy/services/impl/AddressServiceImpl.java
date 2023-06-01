package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.AddressDTO;
import com.newbies.birdy.entities.Address;
import com.newbies.birdy.entities.User;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.AddressMapper;
import com.newbies.birdy.repositories.AddressRepository;
import com.newbies.birdy.repositories.UserRepository;
import com.newbies.birdy.services.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public Integer createAddress(Integer userId, AddressDTO addressDTO) {
        Integer id = 0;

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can not find user have id: " + userId));

        addressDTO.setIsDefault(user.getAddressList() == null);
        addressDTO.setUserId(userId);
        Address prev = AddressMapper.INSTANCE.toEntity(addressDTO);
        prev.setStatus(true);
        Address address = addressRepository.save(prev);
        if(address != null){
            return address.getId();
        }
        return id;
    }
}
