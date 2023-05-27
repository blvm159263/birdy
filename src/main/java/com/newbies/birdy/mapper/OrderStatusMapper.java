package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.OrderStatusDTO;
import com.newbies.birdy.entities.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderStatusMapper {

    OrderStatusMapper INSTANCE = Mappers.getMapper(OrderStatusMapper.class);

    OrderStatusDTO toDTO(OrderStatus orderStatus);

    @Mapping(target = "orderStatusDetailList", ignore = true)
    OrderStatus toEntity(OrderStatusDTO dto);
}
