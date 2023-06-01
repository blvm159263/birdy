package com.newbies.birdy.services;

import com.newbies.birdy.dto.AuthenticationRequest;
import com.newbies.birdy.dto.AuthenticationResponse;
import com.newbies.birdy.dto.RegisterRequest;
import com.newbies.birdy.dto.UserDTO;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    UserDTO createUser(RegisterRequest registerRequest);
}
