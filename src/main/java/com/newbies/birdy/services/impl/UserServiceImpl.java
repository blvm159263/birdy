package com.newbies.birdy.services.impl;

import com.newbies.birdy.repositories.AccountRepository;
import com.newbies.birdy.repositories.UserRepository;
import com.newbies.birdy.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


}
