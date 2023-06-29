package com.newbies.birdy.services;

import com.newbies.birdy.dto.ShipmentDTO;
import com.newbies.birdy.dto.ShopDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ShopService {

    List<ShopDTO> listByShopName(String name, Boolean status);

    ShopDTO getShopById(Integer id);

    List<ShopDTO> listAllShop(Boolean status);

    Map<List<ShopDTO>, Integer> listByNameAndStatusWithPaging(String name, Boolean status, Pageable pageable);
    
    String getShopAddress(Integer shopId);

    List<ShipmentDTO> listShipmentByShopId(Integer shopId, Boolean status);

    ShopDTO getShopByPhoneNumber(String phoneNumber, Boolean status);
}
