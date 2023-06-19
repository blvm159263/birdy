package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Account;
import com.newbies.birdy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByAccountUser(Account accountUser);

    List<User> findByFullNameContainingIgnoreCaseAndStatus(String search, Boolean status);
}
