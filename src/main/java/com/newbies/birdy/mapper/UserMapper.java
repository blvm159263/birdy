package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.UserDTO;
import com.newbies.birdy.entities.Account;
import com.newbies.birdy.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "phoneNumber", source = "accountUser.phoneNumber")
    @Mapping(target = "accountId", source = "accountUser.id")
    UserDTO toDTO(User user);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentMethodList", ignore = true)
    @Mapping(target = "addressList", ignore = true)
    @Mapping(target = "accountUser", source = "accountId", qualifiedByName = "mapAccount")
    User toEntity(UserDTO dto);

    @Named("mapAccount")
    default Account mapAccount(Integer id) {
        Account a = new Account();
        a.setId(id);
        return a;
    }
}
