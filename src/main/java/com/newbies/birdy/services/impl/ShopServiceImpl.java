package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.ShopDTO;
import com.newbies.birdy.entities.Shop;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.ShopMapper;
import com.newbies.birdy.repositories.ShopRepository;
import com.newbies.birdy.services.ShopService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

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
}
