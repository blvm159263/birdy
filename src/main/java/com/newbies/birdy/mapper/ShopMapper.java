package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.ShopDTO;
import com.newbies.birdy.entities.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopMapper {

    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    ShopDTO toDTO(Shop shop);

    @Mapping(target = "shipmentList", ignore = true)
    @Mapping(target = "productList", ignore = true)
    Shop toEntity(ShopDTO dto);

}
