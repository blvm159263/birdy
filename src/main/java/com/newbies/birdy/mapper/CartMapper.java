package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.CartDTO;
import com.newbies.birdy.entities.Cart;
import com.newbies.birdy.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);


    @Mapping(target = "userId", source = "userCart.id")
    @Mapping(target = "fullName", source = "userCart.fullName")
    CartDTO toDTO(Cart cart);


    @Mapping(target = "cartDetailList", ignore = true)
    @Mapping(target = "userCart", source = "userId", qualifiedByName = "mapUser")
    Cart toEntity(CartDTO dto);

    @Named("mapUser")
    default User mapUser(Integer id) {
        User u = new User();
        u.setId(id);
        return u;
    }
}
