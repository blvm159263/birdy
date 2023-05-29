package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.ShopDTO;
import com.newbies.birdy.entities.Account;
import com.newbies.birdy.entities.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopMapper {

    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    @Mapping(target = "accountId", source = "accountShop.id")
    @Mapping(target = "phoneNumber", source = "accountShop.phoneNumber")
    ShopDTO toDTO(Shop shop);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "accountShop", source = "accountId", qualifiedByName = "mapAccount")
    @Mapping(target = "shipmentList", ignore = true)
    @Mapping(target = "productList", ignore = true)
    Shop toEntity(ShopDTO dto);

    @Named("mapAccount")
    default Account mapAccount(Integer id) {
        Account a = new Account();
        a.setId(id);
        return a;
    }

}
