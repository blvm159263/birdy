package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.UserDTO;
import com.newbies.birdy.entities.Role;
import com.newbies.birdy.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roleName", source = "role.id")
    @Mapping(target = "roleId", source = "role.roleName")
    UserDTO toDTO(User user);

    @Mapping(target = "shop", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    @Mapping(target = "addressList", ignore = true)
    @Mapping(target = "role", source = "roleId", qualifiedByName = "mapRole")
    User toEntity(UserDTO dto);

    @Named("mapRole")
    default Role mapRole(Integer id) {
        Role role = new Role();
        role.setId(id);
        return role;
    }
}
