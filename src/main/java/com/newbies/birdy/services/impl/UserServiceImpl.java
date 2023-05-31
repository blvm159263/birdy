package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.UserDTO;
import com.newbies.birdy.dto.UserInformationDTO;
import com.newbies.birdy.entities.Account;
import com.newbies.birdy.entities.User;
import com.newbies.birdy.mapper.UserMapper;
import com.newbies.birdy.repositories.AccountRepository;
import com.newbies.birdy.repositories.UserRepository;
import com.newbies.birdy.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(UserInformationDTO userInformationDTO) {
        Account account = accountRepository
                .save(new Account(null, userInformationDTO.getPhoneNumber(),
                        userInformationDTO.getPassword(), true, null, null));
        User nuser = new User(null, userInformationDTO.getFullName(), userInformationDTO.getEmail(),
                userInformationDTO.getDob(), userInformationDTO.getGender(), null, new Date(),
                true, account, null, null);
        User user = userRepository
                .save(new User(null, userInformationDTO.getFullName(), userInformationDTO.getEmail(),
                        userInformationDTO.getDob(), userInformationDTO.getGender(), null, new Date(),
                        true, account, null, null));
        return UserMapper.INSTANCE.toDTO(user);
    }
}
