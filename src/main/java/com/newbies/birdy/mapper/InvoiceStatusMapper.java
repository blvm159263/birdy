package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.InvoiceStatusDTO;
import com.newbies.birdy.entities.InvoiceStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InvoiceStatusMapper {

    InvoiceStatusMapper INSTANCE = Mappers.getMapper(InvoiceStatusMapper.class);

    InvoiceStatusDTO toDTO(InvoiceStatus invoiceStatus);

    @Mapping(target = "invoiceList", ignore = true)
    InvoiceStatus toEntity(InvoiceStatusDTO dto);
}
