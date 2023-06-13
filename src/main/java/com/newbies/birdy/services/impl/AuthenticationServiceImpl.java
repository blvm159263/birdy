package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.AuthenticationRequest;
import com.newbies.birdy.dto.AuthenticationResponse;
import com.newbies.birdy.dto.RegisterRequest;
import com.newbies.birdy.dto.UserDTO;
import com.newbies.birdy.entities.Account;
import com.newbies.birdy.entities.Role;
import com.newbies.birdy.entities.User;
import com.newbies.birdy.mapper.UserMapper;
import com.newbies.birdy.repositories.AccountRepository;
import com.newbies.birdy.repositories.UserRepository;
import com.newbies.birdy.security.jwt.JwtService;
import com.newbies.birdy.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword()));
        var account = accountRepository.findByPhoneNumberAndStatus(request.getPhoneNumber(), true)
                .orElseThrow();
        var jwtToken = jwtService.generateToken(account);
        var roleName = account.getRole().name();
        return AuthenticationResponse.builder().token(jwtToken).roleName(roleName).build();
    }

    @Override
    public UserDTO createUser(RegisterRequest userInformationDTO) {
        Account account = accountRepository
                .save(new Account(null, userInformationDTO.getPhoneNumber(),
                        passwordEncoder.encode(userInformationDTO.getPassword()) , Role.valueOf("USER"), true, null, null));
        User user = userRepository
                .save(new User(null, userInformationDTO.getFullName(), userInformationDTO.getEmail(),
                        userInformationDTO.getDob(), userInformationDTO.getGender(), null, new Date(),
                        true, account, null, null));
        return UserMapper.INSTANCE.toDTO(user);
    }
}
