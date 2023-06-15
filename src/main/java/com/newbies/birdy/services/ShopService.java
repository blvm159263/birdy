package com.newbies.birdy.services;

import com.newbies.birdy.dto.ShopDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ShopService {

    List<ShopDTO> listByShopName(String name, Boolean status);

    ShopDTO getShopById(Integer id);

    List<ShopDTO> listAllShop(Boolean status);

    Map<List<ShopDTO>, Integer> listByNameAndStatusWithPaging(String name, Boolean status, Pageable pageable);
    
}
