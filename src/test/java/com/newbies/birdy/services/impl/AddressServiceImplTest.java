package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.AddressDTO;
import com.newbies.birdy.entities.Account;
import com.newbies.birdy.entities.Address;
import com.newbies.birdy.entities.User;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.AddressMapper;
import com.newbies.birdy.repositories.AddressRepository;
import com.newbies.birdy.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AddressServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AddressServiceImplTest {
    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private AddressServiceImpl addressServiceImpl;


    User user1 = new User(1, "Bui Minh", "buiminh@gmail.com", new Date(),
            1, "img", new Date(),true, new Account(), null,null);
    User user2 = new User(2, "Tommy", "tommy@gmail.com", new Date(),
            1, "img",new Date(), true, new Account(), null,null);
    Address address1 = new Address(1, "22 le lai", "phuong 4", "TP. Vũng Tàu",
            "Tỉnh Bà Rịa - Vũng Tàu", true, true, null);
    Address address2 = new Address(2, "1/2/3 truong cong dinh", "phuong 8", "TP. Vũng Tàu",
            "Tỉnh Bà Rịa - Vũng Tàu", false, true, null);
    Address address3 = new Address(3, "23 duong 138", "Long thanh my", "TP. Thu duc",
            "Ho Chi Minh", false, true, user1);


    /**
     * Method under test: {@link AddressServiceImpl#createAddress(Integer, AddressDTO)}
     */
    @Test
    void canCreateAddress() {

        List<Address> addressList = new ArrayList<>(Arrays.asList(address1,address2));
        user1.setAddressList(addressList);
        Address prev = address3;
        prev.setId(null);
        User user = new User();
        user.setId(1);
        prev.setUserAddress(user);
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(addressRepository.save(prev)).thenReturn(address3);

        Integer result = addressServiceImpl.createAddress(1, AddressMapper.INSTANCE.toDTO(prev));

        assertEquals(3, 3);
        verify(addressRepository).save(prev);
        verify(userRepository).findById(1);

    }

    /**
     * Method under test: {@link AddressServiceImpl#createAddress(Integer, AddressDTO)}
     */
    @Test
    void itShouldThrowWhenGivenWrongUserId() {

        when(userRepository.findById(3)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressServiceImpl.createAddress(3, AddressMapper.INSTANCE.toDTO(address3)));

        verify(userRepository).findById(3);
    }

}

