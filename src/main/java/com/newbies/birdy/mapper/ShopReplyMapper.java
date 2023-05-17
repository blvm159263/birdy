package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.ShopReplyDTO;
import com.newbies.birdy.entities.Review;
import com.newbies.birdy.entities.Shop;
import com.newbies.birdy.entities.ShopReply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopReplyMapper {

    ShopReplyMapper INSTANCE = Mappers.getMapper(ShopReplyMapper.class);

    @Mapping(target = "shopId", source = "shopReply.id")
    @Mapping(target = "reviewId", source = "reviewShopReply.id")
    ShopReplyDTO toDTO(ShopReply shopReply);

    @Mapping(target = "shopReply", source = "shopId", qualifiedByName = "mapShop")
    @Mapping(target = "reviewShopReply", source = "reviewId", qualifiedByName = "mapReview")
    ShopReply toEntity(ShopReplyDTO dto);

    @Named("mapShop")
    default Shop mapShop(Integer id) {
        Shop shop = new Shop();
        shop.setId(id);
        return shop;
    }

    @Named("mapReview")
    default Review mapReview(Integer id) {
        Review review = new Review();
        review.setId(id);
        return review;
    }
}
