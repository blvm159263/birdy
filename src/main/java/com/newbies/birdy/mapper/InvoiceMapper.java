package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.InvoiceDTO;
import com.newbies.birdy.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);


    @Mapping(target = "invoiceParentId", source = "invoice.id")
    @Mapping(target = "shopName", source = "shopInvoice.shopName")
    @Mapping(target = "shopId", source = "shopInvoice.id")
    @Mapping(target = "paymentTypeName", source = "paymentMethod.paymentType.paymentTypeName")
    @Mapping(target = "paymentMethodId", source = "paymentMethod.id")
    @Mapping(target = "orderId", source = "orderInvoice.id")
    @Mapping(target = "invoiceStatusName", source = "invoiceStatus.invoiceStatusName")
    @Mapping(target = "invoiceStatusId", source = "invoiceStatus.id")
    InvoiceDTO toDTO(Invoice invoice);

    @Mapping(target = "invoiceList", ignore = true)
    @Mapping(target = "shopInvoice", source = "shopId", qualifiedByName = "mapShop")
    @Mapping(target = "paymentMethod", source = "paymentMethodId", qualifiedByName = "mapPaymentMethod")
    @Mapping(target = "orderInvoice", source = "orderId", qualifiedByName = "mapOrder")
    @Mapping(target = "invoiceStatus", source = "invoiceStatusId", qualifiedByName = "mapInvoiceStatus")
    @Mapping(target = "invoice", source = "invoiceParentId", qualifiedByName = "mapInvoice")
    Invoice toEntity(InvoiceDTO dto);

    @Named("mapShop")
    default Shop mapShop(Integer id) {
        Shop shop = new Shop();
        shop.setId(id);
        return shop;
    }

    @Named("mapPaymentMethod")
    default PaymentMethod mapPaymentMethod(Integer id) {
        PaymentMethod pm = new PaymentMethod();
        pm.setId(id);
        return pm;
    }

    @Named("mapOrder")
    default Order mapOrder(Integer id) {
        Order o = new Order();
        o.setId(id);
        return o;
    }

    @Named("mapInvoiceStatus")
    default InvoiceStatus mapInvoiceStatus(Integer id) {
        InvoiceStatus is = new InvoiceStatus();
        is.setId(id);
        return is;
    }

    @Named("mapInvoice")
    default Invoice mapInvoice(Integer id) {
        Invoice i = new Invoice();
        i.setId(id);
        return i;
    }
}
