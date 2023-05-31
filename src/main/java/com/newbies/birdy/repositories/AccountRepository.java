package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByPhoneNumberAndStatus(String phoneNumber, Boolean status);
}
