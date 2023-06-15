package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.ShipmentDTO;
import com.newbies.birdy.dto.ShopDTO;
import com.newbies.birdy.entities.Shipment;
import com.newbies.birdy.entities.Shop;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.ShipmentMapper;
import com.newbies.birdy.mapper.ShopMapper;
import com.newbies.birdy.repositories.ShipmentRepository;
import com.newbies.birdy.repositories.ShopRepository;
import com.newbies.birdy.services.ShopService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    private final ShipmentRepository shipmentRepository;

    @Override
    public List<ShopDTO> listByShopName(String name, Boolean status) {
        List<Shop> shopList = shopRepository.findByShopNameContainingAndStatus(name, status);
        return shopList.stream().map(ShopMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public ShopDTO getShopById(Integer id) {
        return ShopMapper.INSTANCE.toDTO(shopRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID shop not found")));
    }

    @Override
    public List<ShopDTO> listAllShop(Boolean status) {
        List<Shop> shopList = shopRepository.findByStatus(status);
        return shopList.stream().map(ShopMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public Map<List<ShopDTO>, Integer> listByNameAndStatusWithPaging(String name, Boolean status, Pageable pageable) {
        Map<List<ShopDTO>, Integer> pair = new HashMap<>();
        Page<Shop> shops = shopRepository.findByStatusAndShopNameContaining(status, name, pageable);
        pair.put(shops.stream().map(ShopMapper.INSTANCE::toDTO).toList(), shops.getTotalPages());
        return pair;
    }

    @Override
    public String getShopAddress(Integer shopId) {
        return shopRepository.findByIdAndStatus(shopId, true).getAddress();
    }

    @Override
    public List<ShipmentDTO> listShipmentByShopId(Integer shopId, Boolean status) {
        Shop shop = new Shop();
        shop.setId(shopId);
        List<Shipment> list = shipmentRepository.
                findByShopShipmentAndStatus(shop, status);
        return list.stream().map(ShipmentMapper.INSTANCE::toDTO).toList();
    }
}
