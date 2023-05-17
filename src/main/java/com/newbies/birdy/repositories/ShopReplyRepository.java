package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.ShopReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopReplyRepository extends JpaRepository<ShopReply, Integer> {
}
