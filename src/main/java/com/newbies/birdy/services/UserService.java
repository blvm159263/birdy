package com.newbies.birdy.services;

import com.newbies.birdy.dto.UserDTO;
import com.newbies.birdy.dto.UserInformationDTO;
import com.newbies.birdy.entities.User;

public interface UserService {

    UserDTO createUser(UserInformationDTO userDTO);
}
