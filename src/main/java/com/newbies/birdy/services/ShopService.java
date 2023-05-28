package com.newbies.birdy.services;

import com.newbies.birdy.dto.ShopDTO;

import java.util.List;

public interface ShopService {

    List<ShopDTO> listByShopName(String name, Boolean status);

    ShopDTO getShopById(Integer id);
}
