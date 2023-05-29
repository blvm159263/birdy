package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.OrderDTO;
import com.newbies.birdy.entities.Order;
import com.newbies.birdy.entities.PaymentMethod;
import com.newbies.birdy.entities.PaymentStatus;
import com.newbies.birdy.entities.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "paymentTypeName", source = "paymentMethod.paymentType.paymentTypeName")
    @Mapping(target = "paymentStatusName", source = "paymentStatus.paymentStatusName")
    @Mapping(target = "paymentStatusId", source = "paymentStatus.id")
    @Mapping(target = "paymentMethodId", source = "paymentMethod.id")
    @Mapping(target = "orderParentId", source = "order.id")
    @Mapping(target = "shipmentTypeName", source = "shipmentOrder.shipmentType.shipmentTypeName")
    @Mapping(target = "shipmentId", source = "shipmentOrder.id")
    OrderDTO toDTO(Order order);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    @Mapping(target = "paymentStatus", source = "paymentStatusId", qualifiedByName = "mapPaymentStatus")
    @Mapping(target = "paymentMethod", source = "paymentMethodId", qualifiedByName = "mapPaymentMethod")
    @Mapping(target = "order", source = "orderParentId", qualifiedByName = "mapOrder")
    @Mapping(target = "orderStatusDetailList", ignore = true)
    @Mapping(target = "orderDetailList", ignore = true)
    @Mapping(target = "shipmentOrder", source = "shipmentId", qualifiedByName = "mapShipment")
    Order toEntity(OrderDTO dto);

    @Named("mapShipment")
    default Shipment mapShipment(Integer id) {
        Shipment s = new Shipment();
        s.setId(id);
        return s;
    }

    @Named("mapOrder")
    default Order mapOrder(Integer id) {
        Order s = new Order();
        s.setId(id);
        return s;
    }

    @Named("mapPaymentMethod")
    default PaymentMethod mapPaymentMethod(Integer id) {
        PaymentMethod s = new PaymentMethod();
        s.setId(id);
        return s;
    }


    @Named("mapPaymentStatus")
    default PaymentStatus mapPaymentStatus(Integer id) {
        PaymentStatus s = new PaymentStatus();
        s.setId(id);
        return s;
    }

}
