package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.CartDetailDTO;
import com.newbies.birdy.entities.Cart;
import com.newbies.birdy.entities.CartDetail;
import com.newbies.birdy.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartDetailMapper {

    CartDetailMapper INSTANCE = Mappers.getMapper(CartDetailMapper.class);

    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "productName", source = "productCartDetail.productName")
    @Mapping(target = "productId", source = "productCartDetail.id")
    CartDetailDTO toDTO(CartDetail cartDetail);

    @Mapping(target = "productCartDetail", source = "productId", qualifiedByName = "mapProduct")
    @Mapping(target = "cart", source = "cartId", qualifiedByName = "mapCart")
    CartDetail toEntity(CartDetailDTO dto);

    @Named("mapCart")
    default Cart mapCart(Integer id) {
        Cart u = new Cart();
        u.setId(id);
        return u;
    }

    @Named("mapProduct")
    default Product mapProduct(Integer id) {
        Product u = new Product();
        u.setId(id);
        return u;
    }


}
