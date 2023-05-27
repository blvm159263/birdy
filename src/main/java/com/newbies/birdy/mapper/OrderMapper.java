package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.OrderDTO;
import com.newbies.birdy.entities.Order;
import com.newbies.birdy.entities.Shipment;
import com.newbies.birdy.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "shipmentTypeName", source = "shipmentOrder.shipmentType.shipmentTypeName")
    @Mapping(target = "shipmentId", source = "shipmentOrder.id")
    @Mapping(target = "userId", source = "userOrder.id")
    @Mapping(target = "fullName", source = "userOrder.fullName")
    OrderDTO toDTO(Order order);

    @Mapping(target = "orderStatusDetailList", ignore = true)
    @Mapping(target = "orderDetailList", ignore = true)
    @Mapping(target = "invoiceList", ignore = true)
    @Mapping(target = "shipmentOrder", source = "shipmentId", qualifiedByName = "mapShipment")
    @Mapping(target = "userOrder", source = "userId", qualifiedByName = "mapUser")
    Order toEntity(OrderDTO dto);

    @Named("mapUser")
    default User mapUser(Integer id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapShipment")
    default Shipment mapShipment(Integer id) {
        Shipment s = new Shipment();
        s.setId(id);
        return s;
    }

}
