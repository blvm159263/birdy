package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.ShopDTO;
import com.newbies.birdy.entities.Shop;
import com.newbies.birdy.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopMapper {

    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    @Mapping(target = "userId", source = "userShop.id")
    ShopDTO toDTO(Shop shop);

    @Mapping(target = "shopReplyList", ignore = true)
    @Mapping(target = "productList", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    @Mapping(target = "userShop", source = "userId", qualifiedByName = "mapUser")
    Shop toEntity(ShopDTO dto);

    @Named("mapUser")
    default User mapUser(Integer id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
