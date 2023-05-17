package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.ReviewDTO;
import com.newbies.birdy.entities.OrderDetail;
import com.newbies.birdy.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "orderDetailId", source = "orderDetail.id")
    ReviewDTO toDTO(Review review);

    @Mapping(target = "shopReply", ignore = true)
    @Mapping(target = "orderDetail", source = "orderDetailId", qualifiedByName = "mapOrderDetail")
    Review toEntity(ReviewDTO dto);

    @Named("mapOrderDetail")
    default OrderDetail mapOrderDetail(Integer id) {
        OrderDetail c = new OrderDetail();
        c.setId(id);
        return c;
    }
}
