package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.OrderStatusDetailDTO;
import com.newbies.birdy.entities.Order;
import com.newbies.birdy.entities.OrderStatus;
import com.newbies.birdy.entities.OrderStatusDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderStatusDetailMapper {

    OrderStatusDetailMapper INSTANCE = Mappers.getMapper(OrderStatusDetailMapper.class);


    @Mapping(target = "orderStatusName", source = "orderStatus.orderStatusName")
    @Mapping(target = "orderStatusId", source = "orderStatus.id")
    @Mapping(target = "orderId", source = "orderStatusDetail.id")
    OrderStatusDetailDTO toDTO(OrderStatusDetail orderStatusDetail);

    @Mapping(target = "orderStatusDetail", source = "orderId", qualifiedByName = "mapOrder")
    @Mapping(target = "orderStatus", source = "orderStatusId", qualifiedByName = "mapOrderStatus")
    OrderStatusDetail toEntity(OrderStatusDetailDTO dto);

    @Named("mapOrder")
    default Order mapOrder(Integer id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }

    @Named("mapOrderStatus")
    default OrderStatus mapOrderStatus(Integer id) {
        OrderStatus os = new OrderStatus();
        os.setId(id);
        return os;
    }
}
