package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.OrderDTO;
import com.newbies.birdy.entities.Order;
import com.newbies.birdy.entities.Shop;
import com.newbies.birdy.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "userId", source = "userOrder.id")
    @Mapping(target = "shopName", source = "shopOrder.shopName")
    @Mapping(target = "shopId", source = "shopOrder.id")
    @Mapping(target = "fullName", source = "userOrder.fullName")
    OrderDTO toDTO(Order order);

    @Mapping(target = "userOrder", source = "userId", qualifiedByName = "mapUser")
    @Mapping(target = "shopOrder", source = "shopId", qualifiedByName = "mapShop")
    Order toEntity(OrderDTO dto);

    @Named("mapUser")
    default User mapUser(Integer id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapShop")
    default Shop mapShop(Integer id) {
        Shop shop = new Shop();
        shop.setId(id);
        return shop;
    }
}
