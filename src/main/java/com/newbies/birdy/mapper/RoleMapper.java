package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.RoleDTO;
import com.newbies.birdy.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDTO(Role role);

    @Mapping(target = "userList", ignore = true)
    Role toEntity(RoleDTO dto);
}
