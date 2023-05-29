package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.ShopDTO;
import com.newbies.birdy.entities.Account;
import com.newbies.birdy.entities.Shop;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.repositories.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ShopServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ShopServiceImplTest {
    @MockBean
    private ShopRepository shopRepository;

    @Autowired
    private ShopServiceImpl shopServiceImpl;

    /**
     * Method under test: {@link ShopServiceImpl#listByShopName(String, Boolean)}
     */
    Shop shop1 = new Shop(1,"Bird shop thu nhat",
            "22 le lai", "img", new Date(), true, new Account(),null, null);
    Shop shop2 = new Shop(2,"Bird shop thu hai",
            "212 nguyen van troi", "img", new Date(),  false,new Account(), null, null);
    Shop shop3 = new Shop(3,"Bird shop thu ba",
            "2/1/2 truong cong dinh", "img", new Date(), true, new Account(),null, null);

    @Test
    void canListByShopName() {
        List<Shop> shopList = new ArrayList<>(Arrays.asList(shop1, shop2, shop3));

        when(shopRepository.findByShopNameContainingAndStatus("o", true))
                .thenReturn(shopList.stream().filter(Shop::getStatus).toList());

        List<ShopDTO> result = shopServiceImpl.listByShopName("o", true);

        assertEquals(2, result.size());
        verify(shopRepository).findByShopNameContainingAndStatus("o", true);
    }

    /**
     * Method under test: {@link ShopServiceImpl#listByShopName(String, Boolean)}
     */
    @Test
    void canListByShopName2() {
        List<Shop> shopList = new ArrayList<>(Arrays.asList(shop1, shop2, shop3));

        when(shopRepository.findByShopNameContainingAndStatus("nhat", true))
                .thenReturn(shopList.stream().filter(s -> s.getShopName().equals("Bird shop thu nhat") && s.getStatus()).toList());
        List<ShopDTO> result = shopServiceImpl.listByShopName("nhat", true);

        assertEquals(1, result.size());
        assertEquals("Bird shop thu nhat", result.get(0).getShopName());
        verify(shopRepository).findByShopNameContainingAndStatus("nhat", true);
    }

    /**
     * Method under test: {@link ShopServiceImpl#getShopById(Integer)}
     */
    @Test
    void canGetShopById() {
        when(shopRepository.findById(3)).thenReturn(Optional.of(shop3));
        ShopDTO result = shopServiceImpl.getShopById(3);
        assertEquals(3, result.getId());
        assertEquals(shop3.getShopName(), result.getShopName());
        assertEquals(shop3.getCreateDate(), result.getCreateDate());
        assertEquals(shop3.getAddress(), result.getAddress());
        verify(shopRepository).findById(3);
    }

    /**
     * Method under test: {@link ShopServiceImpl#getShopById(Integer)}
     */
    @Test
    void itShouldThrowWhenGivenWrongId() {

        when(shopRepository.findById(4))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> shopServiceImpl.getShopById(4));
        verify(shopRepository).findById(4);
    }


    /**
     * Method under test: {@link ShopServiceImpl#listAllShop(Boolean)}
     */
    @Test
    void canListAllShop() {
        List<Shop> shopList = new ArrayList<>(Arrays.asList(shop1, shop2, shop3));

        when(shopRepository.findByStatus(true))
                .thenReturn(shopList.stream().filter(Shop::getStatus).toList());

        List<ShopDTO> result = shopServiceImpl.listAllShop(true);

        assertEquals(2, result.size());

        verify(shopRepository).findByStatus((Boolean) any());
    }

}

