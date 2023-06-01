package com.newbies.birdy.services;

import com.newbies.birdy.dto.AccountDTO;

public interface AccountService {

    AccountDTO getByPhoneNumber(String phoneNumber, Boolean status);
}
